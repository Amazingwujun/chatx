#include <QApplication>
#include "signinbox.h"

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    SignInBox w;
    w.show();
    return QApplication::exec();
}