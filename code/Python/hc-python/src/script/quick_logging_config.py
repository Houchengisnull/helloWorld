import logging

logging.basicConfig(level=logging.INFO
                    , format='%(asctime)s - %(levelname)-8s - %(filename)-10s:%(lineno)d - %(message)s'
                    # , datefmt='%m-%d %H:%M'
                    , filename='./logs/quick.log'
                    , filemode='a'
                    , encoding='UTF-8')

console = logging.StreamHandler()
console.setLevel(logging.INFO)
formatter = logging.Formatter('%(asctime)s - %(message)s')
console.setFormatter(formatter)
logging.getLogger().addHandler(console)

# 关闭日志传播
## 由于paddle框架bug，若不关闭则无法正常打印日志
logging.propagate = False


if __name__ == '__main__':
    logging.debug("这是一个debug日志")
    logging.info("这是一个info日志")
    logging.warning("这是一个warning日志")