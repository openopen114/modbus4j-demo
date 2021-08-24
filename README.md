# modbus4j
使用 modbus4j 讀取  XY MD02 溫度濕度



過程遇到的坑紀錄一下＠＠

### no rxtxSerial in java.library.path (Mac)

可參考：http://blog.brianhemeryck.me/installing-rxtx-on-mac-os-mountain-lion/

裡面 librxtxSerial.jnilib 連結失效了, 可在http://iharder.sourceforge.net/current/java/

或是 https://drive.google.com/drive/folders/1QI9LFfZKw5PpOaNSvUexKOwWKDLuwkTI?usp=sharing 下載







### gnu.io.PortInUseException: Unknown Application

可參考:https://www.88cto.com/article/WFS0hdbl

```
sudo mkdir /var/lock
sudo chmod go+rwx /var/lock
```







ref:https://github.com/wu-boy/modbus4j









