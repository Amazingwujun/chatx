cmake_minimum_required(VERSION 3.25)
project(ChatX)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH "D:/Qt/Qt5.14.2/5.14.2/mingw73_64/bin")

file(GLOB QRC ./res.qrc)
file(GLOB WIDGET ./src/widget/*.cpp ./src/widget/*.h)

include_directories(./src)
include_directories(./src/widget)

find_package(Qt5 COMPONENTS
        Core
        Gui
        Widgets
        Network
        REQUIRED)

add_executable(ChatX src/main.cpp ${WIDGET} ${QRC})
target_link_libraries(ChatX
        Qt5::Core
        Qt5::Gui
        Qt5::Widgets
        Qt5::Network
        )




