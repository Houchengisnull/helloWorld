import logging

logging.basicConfig(level=logging.DEBUG
                    , format='%(asctime)s - %(levelname)-8s - %(filename)-10s:%(lineno)d - %(message)s'
                    # , datefmt='%m-%d %H:%M'
                    , filename='./logs/douyin.log'
                    , filemode='w'
                    , encoding='UTF-8')

console = logging.StreamHandler()
console.setLevel(logging.INFO)
formatter = logging.Formatter('%(asctime)s - %(message)s')
console.setFormatter(formatter)
logging.getLogger().addHandler(console)


if __name__ == '__main__':
    logging.debug("这是一个debug日志")
    logging.info("这是一个info日志")
    logging.warning("这是一个warning日志")