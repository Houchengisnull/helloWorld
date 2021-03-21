# 三月本金
amount = 25222.96
rate = 0.0026
month = 3
salary = 15000


def cal_next_amount(amount):
    return round(amount * rate, 2)


while month < 270:
    income = cal_next_amount(amount)
    month += 1
    print("第" + str((month)) + "月收益:" + str(income) + ", 本金:" + str(round(amount + salary + income, 2)))
    amount = round((amount + salary + income), 2)
