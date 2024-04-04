[toc]

# 后缀解析

| 后缀 | 含义         | 默认         | 区间   |
| ---- | ------------ | ------------ | ------ |
| --aw | 宽高比       |              |        |
| --c  | 多样性       | 0            | 0-100  |
| --s  | 风格化       | 100          | 0-1000 |
| --q  | 渲染质量     | 0.25\|0.5\|1 | 0.25   |
| --iw | 上传图片权重 | 1            | 0-2    |

## --aw

常用16:9\2:3\1:1

## --s

--s 650

数值越大与prompt差异越大

## --iw

数值越大与prompt差异可能越大

# Prompt

## style

Anime 日式动画



Pixel art 像素

Watercolour 水彩

Dark fantasy 黑暗幻想

Pastel drawing 粉笔

Film style 电影

Oil painting 油画



Comic book 美漫

Cartoon 卡通

Cyberpunk 赛博朋克

## artist

Shinkai Makoto 新海诚

Kyoto Anime 日漫

Pixar 皮克斯

Hayao Miyazaki 宫崎骏

Charlie Bowater

Carl Barks 唐老鸭

Disney 2D

Disney 3D

Cartoon Network 卡通网路，例如超能陆战队

DreamWorks	梦工厂，例如驯龙高手

Nickelodeon	尼克动画，例如海绵宝宝

IP by Popmart 泡泡马特

## 拍摄角度

Aerial View 航拍

Panorama 全景

Telephoto Lens 长焦镜头

Close up特写

Full body 全身

Profile	侧身

Portrail	肖像

Chest shot 胸部以上

Face shot 脸部特写

Waist shot 腰部以上

bokeh	虚化

## 其他

Unreal engine 虚幻引擎

# AI头像

## 日漫

1. 确定`--ar`比例

2. 风格	Anime 

3. 执行5次

   ``` 
   /imagine https://cdn.discordapp.com/attachments/1224077762189332585/1224427745652768868/me.jpg?ex=661d7433&is=660aff33&hm=10fe9d587286f2930faf5e282d12cb3f9bcbe453bf2dee71231593649ec9e7ea& Anime,teenager,gray coat,black hair --niji --r 5  --s 50 --v 6
   ```

   