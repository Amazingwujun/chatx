/********************************************************************************
** Form generated from reading UI file 'signinbox.ui'
**
** Created by: Qt User Interface Compiler version 5.14.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_SIGNINBOX_H
#define UI_SIGNINBOX_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_SignInBox
{
public:
    QVBoxLayout *verticalLayout;
    QSpacerItem *verticalSpacer;
    QLabel *label;
    QLineEdit *username;
    QLineEdit *password;
    QLabel *err_msg;
    QPushButton *sign_in_btn;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer;
    QPushButton *regsiter_btn;
    QSpacerItem *verticalSpacer_2;

    void setupUi(QWidget *SignInBox)
    {
        if (SignInBox->objectName().isEmpty())
            SignInBox->setObjectName(QString::fromUtf8("SignInBox"));
        SignInBox->resize(311, 455);
        SignInBox->setStyleSheet(QString::fromUtf8("QWidget {\n"
"  background-color: #ffffff;\n"
"}\n"
"\n"
"QLineEdit {\n"
"  border: 1px solid #A0A0A0;\n"
"  border-radius: 3px;\n"
"  padding: 5px;\n"
"  font-family: \"Microsoft YaHei\";\n"
"  font-size: 10pt;\n"
"  color: #A0A0A0;\n"
"}\n"
"\n"
"QPushButton {\n"
"  font-family: \"Microsoft YaHei\";\n"
"}\n"
"\n"
"QLineEdit[echoMode=\"2\"] { \n"
"	lineedit-password-character: 9679;\n"
"	lineedit-password-mask-delay: 1000;\n"
"}"));
        verticalLayout = new QVBoxLayout(SignInBox);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        verticalSpacer = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer);

        label = new QLabel(SignInBox);
        label->setObjectName(QString::fromUtf8("label"));
        label->setStyleSheet(QString::fromUtf8("QLabel {\n"
"  font-size: 50pt;\n"
"  font-family: \"Microsoft YaHei\";\n"
"  margin-bottom: 30px;\n"
"}"));
        label->setAlignment(Qt::AlignHCenter|Qt::AlignTop);

        verticalLayout->addWidget(label);

        username = new QLineEdit(SignInBox);
        username->setObjectName(QString::fromUtf8("username"));

        verticalLayout->addWidget(username);

        password = new QLineEdit(SignInBox);
        password->setObjectName(QString::fromUtf8("password"));

        verticalLayout->addWidget(password);

        err_msg = new QLabel(SignInBox);
        err_msg->setObjectName(QString::fromUtf8("err_msg"));
        err_msg->setEnabled(true);
        err_msg->setStyleSheet(QString::fromUtf8("QLabel {\n"
"  font-size: 10pt;\n"
"  font-family: \"Microsoft YaHei\";\n"
"  color: red;\n"
"}"));

        verticalLayout->addWidget(err_msg);

        sign_in_btn = new QPushButton(SignInBox);
        sign_in_btn->setObjectName(QString::fromUtf8("sign_in_btn"));
        sign_in_btn->setEnabled(true);
        sign_in_btn->setStyleSheet(QString::fromUtf8("QPushButton {\n"
"  border-radius: 3px;\n"
"  background-color: #07c160;\n"
"  color: #ffffff;\n"
"  padding: 5px;\n"
"  font-size: 12pt;\n"
"}\n"
"\n"
"QPushButton:hover {\n"
"  background-color: #38cd7f;\n"
"}\n"
"\n"
"QPushButton:disabled {\n"
"  background-color: grey;\n"
"}"));

        verticalLayout->addWidget(sign_in_btn);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        regsiter_btn = new QPushButton(SignInBox);
        regsiter_btn->setObjectName(QString::fromUtf8("regsiter_btn"));
        regsiter_btn->setCursor(QCursor(Qt::PointingHandCursor));
        regsiter_btn->setStyleSheet(QString::fromUtf8("QPushButton {\n"
"  border: 0px;\n"
"  color: #436895;\n"
"  font-size: 10pt;\n"
"}"));

        horizontalLayout->addWidget(regsiter_btn);


        verticalLayout->addLayout(horizontalLayout);

        verticalSpacer_2 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer_2);


        retranslateUi(SignInBox);

        QMetaObject::connectSlotsByName(SignInBox);
    } // setupUi

    void retranslateUi(QWidget *SignInBox)
    {
        SignInBox->setWindowTitle(QCoreApplication::translate("SignInBox", "SignInBox", nullptr));
        label->setText(QCoreApplication::translate("SignInBox", "ChatX", nullptr));
        username->setPlaceholderText(QCoreApplication::translate("SignInBox", "\350\264\246\345\217\267", nullptr));
        password->setPlaceholderText(QCoreApplication::translate("SignInBox", "\345\257\206\347\240\201", nullptr));
        err_msg->setText(QCoreApplication::translate("SignInBox", "\351\224\231\350\257\257\344\277\241\346\201\257", nullptr));
        sign_in_btn->setText(QCoreApplication::translate("SignInBox", "\347\231\273\345\205\245", nullptr));
        regsiter_btn->setText(QCoreApplication::translate("SignInBox", "\346\263\250\345\206\214\350\264\246\345\217\267", nullptr));
    } // retranslateUi

};

namespace Ui {
    class SignInBox: public Ui_SignInBox {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_SIGNINBOX_H
