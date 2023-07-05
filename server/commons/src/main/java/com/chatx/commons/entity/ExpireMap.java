package com.chatx.commons.entity;

import com.chatx.commons.Immutable;
import com.chatx.commons.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 简单的过期缓存 map
 *
 * @author Jun
 * @since 1.0.0
 */
public class ExpireMap<K, V> implements Map<K, V>, Runnable {

    private static final Logger log = LoggerFactory.getLogger(ExpireMap.class);
    private final Duration defaultExpireTime;
    private final Map<K, V> map = new ConcurrentHashMap<>(256);
    private final DelayQueue<ExpireKey<K>> delayQueue = new DelayQueue<>();
    private final Listener<K, V> listener;

    /**
     * 构建不带有默认时间的实例
     */
    public ExpireMap() {
        this(null);
    }

    /**
     * 构建带有默认过期时间的实例
     *
     * @param defaultExpireTime 默认过期时间
     */
    public ExpireMap(Duration defaultExpireTime) {
        this(defaultExpireTime, null);
    }

    public ExpireMap(Duration defaultExpireTime, Listener<K, V> listener) {
        this.defaultExpireTime = defaultExpireTime;
        this.listener = listener;
        new Thread(this).start();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                var expireKey = delayQueue.poll();
                if (expireKey == null) {
                    expireKey = delayQueue.take();
                }
                var v = map.remove(expireKey.key);

                // 监听过期事件
                if (v != null && listener != null) {
                    listener.action(new Entry<>(expireKey.key, v));
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 见 {@link Map#put(Object, Object)}
     *
     * @param key        与指定值关联的键
     * @param value      要与指定键关联的值
     * @param expireTime 键过期时间
     * @return 与key关联的先前值，如果没有key 的映射，则为null 。 （如果实现支持null值，则返回null还可以指示映射先前将null与key关联。）
     */
    public V put(K key, V value, Duration expireTime) {
        if (expireTime == null && defaultExpireTime == null) {
            throw new IllegalArgumentException("方法入参 expireTime 与构建对象时的参数 defaultExpireTime 不能同时为空");
        }

        V val = map.put(key, value);
        delayQueue.add(new ExpireKey<>(key, expireTime == null ? defaultExpireTime : expireTime));

        return val;
    }

    /**
     * @see #put(Object, Object, Duration)
     */
    @Override
    public V put(K key, V value) {
        return put(key, value, null);
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Assert.isTrue(m instanceof ExpireMap, "m 必须是 ExpireMap 的实例");
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public V compute(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return this.map.compute(key, remappingFunction);
    }

    @Override
    public V computeIfAbsent(K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        return this.map.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return this.map.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    private static class ExpireKey<K> implements Delayed {
        public final K key;
        public final long expireTimestamp;

        public ExpireKey(K key, Duration expireTime) {
            Assert.notNull(expireTime, "expireTime can't be null");

            this.key = key;
            this.expireTimestamp = System.currentTimeMillis() + expireTime.toMillis();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTimestamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
    }

    @Immutable
    public static class Entry<K, V> implements Map.Entry<K, V> {
        private final K k;
        private final V v;

        public Entry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(Object value) {
            throw new UnsupportedOperationException("不支持 setValue 操作");
        }
    }

    public interface Listener<K, V> {

        void action(Entry<K, V> entry);
    }
}
