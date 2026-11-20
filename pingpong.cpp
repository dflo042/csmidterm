#include <iostream>
using namespace std;

// Problem 4 Part A
int main() {
    for (int n = 96; n >= 1; n--) {
        bool six = (n % 6 == 0);
        bool eight = (n % 8 == 0);

        if (six && eight) {
            continue;
        } else if (six) {
            cout << "Ping" << endl;
        } else if (eight) {
            cout << "Pong" << endl;
        } else {
            cout << n << endl;
        }
    }
    return 0;
}
