# 올스자 애플리케이션
해당 애플리케이션은 거북목 증후군 예방과 건강한 휴대폰 사용을 위해서 개발되었습니다.


## 올스자(올바른 스마트폰 자세) 목차
[1. 개요](#1-개요)<br>
[2. 기능](#2-기능)<br>
   &nbsp;&nbsp;&nbsp;[2.1 휴대폰 각도](#21-휴대폰-각도)<br>
   &nbsp;&nbsp;&nbsp;[2.2 휴대폰과 사용자 사이에 거리](#22-휴대폰과-사용자-사이에-거리)<br>
   &nbsp;&nbsp;&nbsp;[2.3 스트레칭 시작하기](#23-스트레칭-시작하기)<br>
[3. 애플리케이션의 동작 방식](#3-애플리케이션의-동작-방식)<br>
   &nbsp;&nbsp;&nbsp;[3.1 휴대폰 각도](#31-휴대폰-각도)<br>
   &nbsp;&nbsp;&nbsp;[3.2 휴대폰과 사용자 사이에 거리](#32-휴대폰과-사용자-사이에-거리)<br>
[4. 참조](#4-참조)<br>
[5. 만든이](#5-만든이)<br>

## 1. 개요

* 거북목은 현대인의 고질병입니다. 
하루에 대부분의 시간을 사용하는 스마트폰이 주된 원인이라고 할수 있는데 거북목 증후군을 내버려두면 수술이 필요한 목디스크까지 악화될 수 있어 주의해야합니다.

* 거북목증후군은 C자를 유지해야 할 정상 목뼈가 1자 또는 역C자 형으로 변형된 증상입니다.
평소 사람의 목은 고개를 들고 있을 때 5kg 정도의 하중을 받는데, 고개를 15도씩 앞으로 숙일수록 2배 이상 하중이 늘어납니다. 
스마트폰을 장시간 사용할 경우 고개는 자연스럽게 45도 정도 기울어지고, 목은 23kg에 달하는 압력을 받는다고합니다.

* 거북목이될경우 어깨의 통증, 목의 통증, 눈의 피로, 불면증, 어지러움이 있을수도있습니다.

* 평소에 관리를 잘해야만 거북목 증후근을 예방을 할수 있습니다.

* 일자목, 거북목, 목디스크의 가장 좋은 예방법은 바로 바른 자세입니다. 

### 예방법
1. 핸드폰을 볼 때는 목을 똑바로 세운 상태에서 눈높이와 비슷한 위치에서 시선을 15도쯤 아래로 내리고, 컴퓨터 모니터의 위치는 자신의 눈높이와 2/3 지점을 맞추고 바라봅니다.
2. 오랜 시간 같은 자세로 있지 않고 15~30분마다 스트레칭 및 휴식한다.

휴대폰에 집중하게되면 어느순간 45도 정도 기울이고 휴대폰을 할수도 있기때문에 어플을 통해 주의를 주려고 합니다.<br>
또한, 거북목을 제외하더라도 휴대폰을 너무 가까이 볼 경우 눈 건강을 해칠 가능성이 있기 때문에 어플을 통하여 주의를 주게 됩니다.<br><br>

## 2. 기능
- 모든 측정은 하단에 "측정 시작하기" 버튼을 누를경우 측정이 시작됩니다.
- 사용자의 얼굴인식이되지 않는 경우에는 알림메세지를 주지 않습니다.

### 2.1 휴대폰 각도
 - 안드로이드 내의 자이로스코프내의 센서를 통한 휴대폰의 각도를 측정합니다.
 - 해당 각도의 기본값은 50도로 각도가 50도 이하로 내려간 상태로 5초이상 유지가 될 경우 고개를 세워달라는 알림 메세지를 줍니다.
### 2.2 휴대폰과 사용자 사이에 거리
 - 휴대폰과 사용자의 거리를 계산하고 화면에 보여주게됩니다.
 - 해당 거리의 기본값은 00cm로 거리가 00cm 보다 가까운 상태로 5초이상 유지가 될 경우 휴대폰을 떨어트려달라는 알림 메세지를 줍니다.
### 2.3 스트레칭 시작하기
 - 장시간 휴대폰을 사용했을 경우 목에 피로를 풀수있는 스트레칭에 대해서 설명해줍니다.
<br><br>
## 3. 애플리케이션의 동작 방식

### 3.1 휴대폰 각도
 #### 3.1.1 가속도계 센서에서의 회전 개념
 ![rotation concept](https://user-images.githubusercontent.com/59985098/93082537-0cfeae00-f6cc-11ea-9861-813eeb8b6488.png)
 
  - 가속도계의 센서를 통해 0도에서 360도까지의 값으로 환산되도록 한 절대 각도입니다.
 
 #### 3.1.2 기기의 회전 모드 종료와 각도 범위
 ![rotation mode](https://user-images.githubusercontent.com/59985098/93082544-0ff99e80-f6cc-11ea-90d9-1cfb60c987af.png)
 
  - 절대각도를 통한 스마트폰 기기의 회전 모드를 측정하는 기준입니다.
  - 또한 절대각도를 이용한 기기의 회전 정도를 구할 수 있습니다.
  
  #### 3.1.3 자이로스코프 센서를 통한 각도 구하기
  ![xyz](https://user-images.githubusercontent.com/59985098/93084576-3c62ea00-f6cf-11ea-8ec4-ba6fd6b24ba3.png)
  
  - 자이로스코프의 센서에서 각 X,Y,Z축에 대한 값을 측정해주며 측정된 값은 각속도이다.
  - 해당 각속도를 가지고 회전각도를 구하면된다.
  - 공식
  ```
   거리 = 속력 * 시간
   회전각 = 각속도 * 시간
   현재까지 회전각 = 이전 회전각 + 측정한 각속도 * dt(적분)
  ```
  

### 3.2 휴대폰과 사용자 사이에 거리
 - 휴대폰과 사용자의 각도를 통해 사용자에게 올바른 거리를 유지시켜줍니다.<br><br>

## 4. 참조
https://m.blog.naver.com/arumizz/221403019145  - 가속도계 센서를 이용하여 회전 각도 구하기

<br><br>

## 5. 만든이
* **김도윤** -*University : Sunkyul Univ* -
* **황혁주** -*University : Sunkyul Univ* -
* **박원용** -*University : Sunkyul Univ* -






