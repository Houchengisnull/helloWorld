import math

# 330
# 收益
income = 100
# 耗时
time = 30
efficiency = income/time
income_per_minute = efficiency * 60
income_per_10_minute = income_per_minute * 10
income_per_hour = income_per_10_minute * 6
income_per_10_hour = income_per_hour * 10

print("----------- ---------- ---------")
print("每时{0}金币".format(income_per_hour))
print("每10时{0}金币".format(income_per_10_hour))
rate = 10000
efficiency_rmb = efficiency / rate
efficiency_rmb_per_minute = efficiency_rmb * 60
efficiency_rmb_per_10_minute = efficiency_rmb_per_minute * 10
efficiency_rmb_per_hour = efficiency_rmb_per_10_minute * 6
efficiency_rmb_per_10_hour = efficiency_rmb_per_hour * 10
efficiency_rmb_per_day = efficiency_rmb_per_hour * 24
efficiency_rmb_per_month = efficiency_rmb_per_day * 30

print("----------- ---------- ---------")
print("每秒{0}元".format(efficiency_rmb))
print("每时{0}元".format(efficiency_rmb_per_hour))
print("每10时{0}元".format(efficiency_rmb_per_10_hour))
print("每天{0}元".format(efficiency_rmb_per_day))
print("每月{0}元".format(efficiency_rmb_per_month))

print("----------- ---------- ---------")
print(63+117+300+276+162+297+330
      +84+38+200+68)

