//
// Created by admin on 2023/7/3.
//

// You may need to build the project (run Qt uic code generator) to get "ui_SignInBox.h" resolved

#include "signinbox.h"
#include "ui_SignInBox.h"


SignInBox::SignInBox(QWidget *parent) :
        QWidget(parent), ui(new Ui::SignInBox) {
    ui->setupUi(this);
    this->setWindowFlag(Qt::WindowMinMaxButtonsHint, false);
    this->setWindowTitle("ChatX");
    this->networkManager = new QNetworkAccessManager(this);

    // err_msg label 处理
    ui->err_msg->setVisible(false);

    // 登录按钮
    connect(ui->sign_in_btn, &QPushButton::clicked, [&]() {
        ui->sign_in_btn->setEnabled(false);
        ui->sign_in_btn->setText("正在登入...");

        // 获取用户名、密码
        auto uname = ui->username->text().trimmed();
        auto passwd = ui->password->text().trimmed();

        QJsonObject data{
                {"account", uname},
                {"passwd",  passwd}
        };
        auto json = QJsonDocument{data};

        auto payload = json.toJson();

        // 创建请求
        QNetworkRequest req;
        req.setUrl({"http://localhost/user-admin/sign-in"});
        req.setRawHeader("Content-Type", "application/json");

        // 发起请求
        auto reply = networkManager->post(req, payload);

        // 处理结果
        connect(reply, &QNetworkReply::finished, [&]() {
            if (reply->error() == QNetworkReply::NoError) {
                // http code  == 200

            } else {
                ui->err_msg->setText(reply->errorString());
                ui->err_msg->setVisible(true);
            }

            reply->deleteLater();
            ui->sign_in_btn->setText("登入");
            ui->sign_in_btn->setEnabled(true);
        });
    });
}

SignInBox::~SignInBox() {
    delete ui;
}

