
#### 关于用户

##### 登录

POST  jay86.box.com:8080/minibox/user/login.do
请求参数： 
| 请求参数        | 参数类型   | 参数描述 |
| ----------- | ------ | ---- |
| password    | String |      |
| phoneNumber | String |      |

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": {
        "userId": 144,
        "userName": "mei22",
        "phoneNumber": "15808060134",
        "sex": "男",
        "email": "1058752198@qq.com",
        "image": null,
        "trueName": null,
        "taken": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTc5MjUxNDIsInVzZXJJZCI6MTQ0fQ.EsU1AvtmZ2wh0N2rUAzGwFt0HT475MZATXoSqtwEELs",
        "useTime": 0,
        "credibility": 100
    }
}
```



##### 注册

POST  jay86.box.com:8080/minibox/user/register.do
请求参数： 
| 请求参数        | 参数类型   | 参数描述 |
| ----------- | ------ | ---- |
| userName    | String |      |
| password    | String |      |
| phoneNumber | String |      |
| sex         | String |      |
| trueName    | String |      |
| email       | String |      |
| verifyCode  | String |      |

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": {
        "userId": 146,
        "userName": "mei23",
        "phoneNumber": "15808060138",
        "sex": "男",
        "email": "1058752198@qq.com",
        "image": null,
        "trueName": null,
        "taken": "",
        "useTime": 0,
        "credibility": 100
    }
}
```



##### 展示用户资料

POST  jay86.box.com:8080/minibox/user/showUserInfo.do
请求参数： 
| 请求参数  | 参数类型   | 参数描述 |
| ----- | ------ | ---- |
| taken | String |      |



##### 修改用户资料

POST  jay86.box.com:8080/minibox/user/updateUserInfo.do
请求参数： 
| 请求参数        | 参数类型   | 参数描述 |
| ----------- | ------ | ---- |
| userName    | String |      |
| phoneNumber | String |      |
| email       | String |      |
| trueName    | String |      |
| taken       | String |      |
| sex         | String |      |

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": {
        "userId": 135,
        "userName": "mei1",
        "phoneNumber": "15808060137",
        "sex": "男",
        "email": "1058752198@qq.com",
        "image": "2222",
        "trueName": "梅勇杰2",
        "taken": "",
        "useTime": 2,
        "credibility": 100
    }
}
```

##### 发送验证码

POST  jay86.box.com:8080/minibox/user/sendSms.do
请求参数： 
| 请求参数        | 参数类型   | 参数描述 |
| ----------- | ------ | ---- |
| phoneNumber | String |      |

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": "921614"
}
```



##### 修改头像

POST  jay86.box.com:8080/minibox/user/updateAvatar.do
请求参数： 
| 请求参数      | 参数类型   | 参数描述 |
| --------- | ------ | ---- |
| taken     | String |      |
| avatarUrl | String |      |

##### 修改密码
POST  jay86.box.com:8080/minibox/user/updatePassword.do
请求参数： 
| 请求参数        | 参数类型   | 参数描述 |
| ----------- | ------ | ---- |
| newPassword | String |      |
| taken       | String |      |


#### 关于箱子
##### 展示用户正在使用的箱子
POST  jay86.box.com:8080/minibox/box/showUserUsingBoxes.do
请求参数： 
| 请求参数  | 参数类型   | 参数描述 |
| ----- | ------ | ---- |
| taken | String |      |

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": [
        {
            "boxId": 601,
            "boxSize": "小",
            "boxStatus": 0,
            "groupName": "重庆市南岸区重庆邮电大学太极体育场西3门",
            "lat": 29.53891,
            "lng": 106.615554,
            "openTime": "2018-01-04 16:38:42.0"
        }
    ]
}
```

##### 展示用户已经预约的箱子

POST  jay86.box.com:8080/box/showUserReservingBoxes.do
请求参数： 
| 请求参数  | 参数类型   | 参数描述 |
| ----- | ------ | ---- |
| taken | String |      |

返回体参数跟上面一样不重复

##### 展示附近的存放点

POST  jay86.box.com:8080/showBoxGroupAround.do
请求参数： 
| 请求参数 | 参数类型   | 参数描述 |
| ---- | ------ | ---- |
| lat  | String |      |
| lng  | String |      |

返回体参数跟上面一样不重复



#### 关于优惠券

##### 添加优惠券
POST  jay86.box.com:8080/minibox/coupon/addCoupon.do
请求参数： 
| 请求参数         | 参数类型   | 参数描述 |
| ------------ | ------ | ---- |
| userId       | String |      |
| money        | String |      |
| deadlineTime | String |      |

##### 删除优惠券
GET  jay86.box.com:8080/minibox/coupon/deleteCoupon.do?couponId=3
请求参数： 

##### 展示某个用户的所有优惠券
POST  jay86.box.com:8080/minibox/coupon/showCoupon.do

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": [
        {
            "couponId": 2,
            "userId": 135,
            "money": 5,
            "deadlineTime": "2017-05-10 00:00:00.0"
        },
        {
            "couponId": 10,
            "userId": 135,
            "money": 5,
            "deadlineTime": "2019-05-10 12:52:00.0"
        },
        {
            "couponId": 11,
            "userId": 135,
            "money": 5,
            "deadlineTime": "2019-05-10 12:52:00.0"
        }
    ]
}
```

 


#### 关于预约
##### 预约
POST  jay86.box.com:8080/minibox/reservation/reserve.do
请求参数： 
| 请求参数        | 参数类型   | 参数描述 |
| ----------- | ------ | ---- |
| boxNum      | String |      |
| openTime    | String |      |
| useTime     | String |      |
| userName    | String |      |
| boxSize     | String |      |
| phoneNumber | String |      |
| groupId     | String |      |
| taken       | String |      |

##### 结束预约并开始订单
POST  jay86.box.com:8080/minibox/reservation/endReserve.do
请求参数： 
| 请求参数          | 参数类型   | 参数描述 |
| ------------- | ------ | ---- |
| reservationId | String |      |
| taken         | String |      |

##### 修改预约的信息
POST  jay86.box.com:8080/minibox/reservation/UpdateReservation.do
请求参数： 
| 请求参数          | 参数类型   | 参数描述 |
| ------------- | ------ | ---- |
| useTime       | String |      |
| reservationId | String |      |
| openTime      | String |      |
| userName      | String |      |
| phoneNumber   | String |      |

##### 得到用户的所有预约
POST  jay86.box.com:8080/minibox/reservation/getReservations.do?
请求参数： 
| 请求参数  | 参数类型   | 参数描述 |
| ----- | ------ | ---- |
| taken | String |      |

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": [
        {
            "reservationId": 125,
            "userId": 135,
            "openTime": "2019-01-08 10:00:00.0",
            "useTime": 3,
            "userName": "mei",
            "phoneNumber": "15808060138",
            "groupId": 6,
            "boxId": 601,
            "boxSize": "小",
            "delFlag": 1,
            "expFlag": 1
        },
        {
            "reservationId": 158,
            "userId": 135,
            "openTime": "2017-10-05 10:05:00.0",
            "useTime": 3,
            "userName": "mei",
            "phoneNumber": "15808060138",
            "groupId": 0,
            "boxId": 102,
            "boxSize": "小",
            "delFlag": 0,
            "expFlag": 1
        },
        {
            "reservationId": 159,
            "userId": 135,
            "openTime": "2019-02-09 18:00:00.0",
            "useTime": 2,
            "userName": "mei",
            "phoneNumber": "15808060138",
            "groupId": 1,
            "boxId": 102,
            "boxSize": "小",
            "delFlag": 0,
            "expFlag": 0
        },
        {
            "reservationId": 160,
            "userId": 135,
            "openTime": "2019-02-09 18:00:00.0",
            "useTime": 2,
            "userName": "mei",
            "phoneNumber": "15808060138",
            "groupId": 1,
            "boxId": 103,
            "boxSize": "小",
            "delFlag": 0,
            "expFlag": 0
        }
    ]
}
```



##### 删除预约

GET  jay86.box.com:8080/minibox/reservation/deleteReservation.do?reservationId=126



#### 关于存放点
##### 展示关于某个地点的所有存放点
POST  jay86.box.com:8080/minibox/group/showBoxGroup.do
请求参数： 
| 请求参数        | 参数类型   | 参数描述 |
| ----------- | ------ | ---- |
| destination | String |      |

返回体示例：

```
{
    "status": 200,
    "message": "请求成功",
    "object": [
        {
            "groupId": 1,
            "quantity": 40,
            "position": "重庆市南岸区南坪万达",
            "lat": 29.5252427172,
            "lng": 106.5585506366,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 9
        },
        {
            "groupId": 2,
            "quantity": 40,
            "position": "重庆市南岸区南坪星光时代",
            "lat": 29.5235257349,
            "lng": 106.5705765784,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 3,
            "quantity": 40,
            "position": "重庆市南岸区南坪公交枢纽",
            "lat": 29.5334359703,
            "lng": 106.5614449573,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 4,
            "quantity": 40,
            "position": "重庆市南岸区重庆邮电大学老校门",
            "lat": 29.538921,
            "lng": 106.610757,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 5,
            "quantity": 40,
            "position": "重庆市南岸区重庆邮电大学新校门",
            "lat": 29.541174,
            "lng": 106.611817,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 6,
            "quantity": 40,
            "position": "重庆市南岸区重庆邮电大学太极体育场西3门",
            "lat": 29.53891,
            "lng": 106.615554,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 7,
            "quantity": 40,
            "position": "重庆市南岸区南山植物园",
            "lat": 29.560988,
            "lng": 106.637701,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 8,
            "quantity": 40,
            "position": "重庆市渝中区解放碑步行街",
            "lat": 29.563503,
            "lng": 106.583614,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 9,
            "quantity": 40,
            "position": "重庆市江北区观音桥好吃街",
            "lat": 29.582244,
            "lng": 106.540436,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        },
        {
            "groupId": 10,
            "quantity": 40,
            "position": "重庆市渝北区江北国际机场",
            "lat": 29.727675,
            "lng": 106.648039,
            "emptySmallBoxNum": 0,
            "emptyLargeBoxNum": 10
        }
    ]
}
```

##### 展示某个存放点

GET  jay86.box.com:8080/minibox/showBoxGroup.do?groupId=1




#### 关于订单
##### 下订单
POST  jay86.box.com:8080/minibox/box/order.do
请求参数： 
| 请求参数    | 参数类型   | 参数描述 |
| ------- | ------ | ---- |
| taken   | String |      |
| groupId | String |      |
| boxSize | String |      |
| boxNum  | String |      |

##### 结束订单
POST  jay86.box.com:8080/minibox/box/endOrder.do
请求参数： 
| 请求参数    | 参数类型   | 参数描述 |
| ------- | ------ | ---- |
| orderId | String |      |
| cost    | String |      |

