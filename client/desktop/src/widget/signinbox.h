//
// Created by admin on 2023/7/3.
//

#ifndef CHATX_SIGNINBOX_H
#define CHATX_SIGNINBOX_H

#include <QWidget>
#include <QDebug>
#include <QNetworkAccessManager>
#include <QJsonObject>
#include <QJsonDocument>
#include <QNetworkReply>
#include <QMessageBox>


QT_BEGIN_NAMESPACE
namespace Ui { class SignInBox; }
QT_END_NAMESPACE

class SignInBox : public QWidget {
Q_OBJECT


public:
    explicit SignInBox(QWidget *parent = nullptr);

    ~SignInBox() override;

private:
    Ui::SignInBox *ui;

    QNetworkAccessManager *networkManager;

    QLabel *errorMsg;
};


#endif //CHATX_SIGNINBOX_H
