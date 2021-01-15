#include<stdio.h>

void cal(int days, float factor) {
    printf("%d days : %0.2f\n", days, factor * days);
}

int main(){
    float factor = 1.34;
    cal(10, factor);
    cal(30, factor);
    cal(365, factor);
    cal(365 * 3, factor);
    return 0;
}