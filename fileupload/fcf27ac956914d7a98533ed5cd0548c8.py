# Sensortag CC2650
# 2019.11.15
# VERSION 1.0
# CHU BOHAO


# import python package
from bluepy.btle import Scanner, DefaultDelegate, Peripheral, UUID, AssignedNumbers
from matplotlib import pyplot
import struct 
import math
import json

def _TI_UUID(val):
    return UUID("%08X-0451-4000-b000-000000000000" % (0xF0000000+val))

# Sensortag versions
AUTODETECT = "-"
SENSORTAG_2650 = "CC2650"


class SensorBase:
    # Derived classes should set: svcUUID, ctrlUUID, dataUUID
    sensorOn  = struct.pack("B", 0x01)
    sensorOff = struct.pack("B", 0x00)

    # Sensor initialization
    def __init__(self, periph):
        self.periph = periph
        self.service = None
        self.ctrl = None
        self.data = None

    def enable(self):
        if self.service is None:
            self.service = self.periph.getServiceByUUID(self.svcUUID)
        if self.ctrl is None:
            self.ctrl = self.service.getCharacteristics(self.ctrlUUID) [0]
        if self.data is None:
            self.data = self.service.getCharacteristics(self.dataUUID) [0]
        if self.sensorOn is not None:
            self.ctrl.write(self.sensorOn,withResponse=True)

    def read(self):
        return self.data.read()

    def disable(self):
        if self.ctrl is not None:
            self.ctrl.write(self.sensorOff)

    # Derived class should implement _formatData()

class IRTemperatureSensorTMP007(SensorBase):
    svcUUID  = _TI_UUID(0xAA00)
    dataUUID = _TI_UUID(0xAA01)
    ctrlUUID = _TI_UUID(0xAA02)

    SCALE_LSB = 0.03125;

    def __init__(self, periph):
        SensorBase.__init__(self, periph)

    def read(self):
        '''Returns (ambient_temp, target_temp) in degC'''
        (rawTobj, rawTamb) = struct.unpack('<hh', self.data.read())
        tObj = (rawTobj >> 2) * self.SCALE_LSB;
        tAmb = (rawTamb >> 2) * self.SCALE_LSB;
        return (tAmb, tObj)

class MovementSensorMPU9250(SensorBase):
    svcUUID  = _TI_UUID(0xAA80)
    dataUUID = _TI_UUID(0xAA81)
    ctrlUUID = _TI_UUID(0xAA82)
    sensorOn = None
    GYRO_XYZ =  7
    ACCEL_XYZ = 7 << 3
    MAG_XYZ = 1 << 6
    ACCEL_RANGE_2G  = 0 << 8
    ACCEL_RANGE_4G  = 1 << 8
    ACCEL_RANGE_8G  = 2 << 8
    ACCEL_RANGE_16G = 3 << 8

    def __init__(self, periph):
        SensorBase.__init__(self, periph)
        self.ctrlBits = 0

    def enable(self, bits):
        SensorBase.enable(self)
        self.ctrlBits |= bits
        self.ctrl.write( struct.pack("<H", self.ctrlBits) )

    def disable(self, bits):
        self.ctrlBits &= ~bits
        self.ctrl.write( struct.pack("<H", self.ctrlBits) )

    def rawRead(self):
        dval = self.data.read()
        return struct.unpack("<hhhhhhhhh", dval)

class AccelerometerSensorMPU9250:
    def __init__(self, sensor_):
        self.sensor = sensor_
        self.bits = self.sensor.ACCEL_XYZ | self.sensor.ACCEL_RANGE_4G
        self.scale = 8.0/32768.0

    def enable(self):
        self.sensor.enable(self.bits)

    def disable(self):
        self.sensor.disable(self.bits)

    def read(self):
        '''Returns (x_accel, y_accel, z_accel) in units of g'''
        rawVals = self.sensor.rawRead()[3:6]
        return tuple([ v*self.scale for v in rawVals ])

class HumiditySensorHDC1000(SensorBase):
    svcUUID  = _TI_UUID(0xAA20)
    dataUUID = _TI_UUID(0xAA21)
    ctrlUUID = _TI_UUID(0xAA22)

    def __init__(self, periph):
        SensorBase.__init__(self, periph)

    def read(self):
        '''Returns (ambient_temp, rel_humidity)'''
        (rawT, rawH) = struct.unpack('<HH', self.data.read())
        temp = -40.0 + 165.0 * (rawT / 65536.0)
        RH = 100.0 * (rawH/65536.0)
        return (temp, RH)

class MagnetometerSensorMPU9250:
    def __init__(self, sensor_):
        self.sensor = sensor_
        self.scale = 4912.0 / 32760
        # Reference: MPU-9250 register map v1.4

    def enable(self):
        self.sensor.enable(self.sensor.MAG_XYZ)

    def disable(self):
        self.sensor.disable(self.sensor.MAG_XYZ)

    def read(self):
        '''Returns (x_mag, y_mag, z_mag) in units of uT'''
        rawVals = self.sensor.rawRead()[6:9]
        return tuple([ v*self.scale for v in rawVals ])

class BarometerSensorBMP280(SensorBase):
    svcUUID  = _TI_UUID(0xAA40)
    dataUUID = _TI_UUID(0xAA41)
    ctrlUUID = _TI_UUID(0xAA42)

    def __init__(self, periph):
        SensorBase.__init__(self, periph)

    def read(self):
        (tL,tM,tH,pL,pM,pH) = struct.unpack('<BBBBBB', self.data.read())
        temp = (tH*65536 + tM*256 + tL) / 100.0
        press = (pH*65536 + pM*256 + pL) / 100.0
        return (temp, press)

class GyroscopeSensorMPU9250:
    def __init__(self, sensor_):
        self.sensor = sensor_
        self.scale = 500.0/65536.0

    def enable(self):
        self.sensor.enable(self.sensor.GYRO_XYZ)

    def disable(self):
        self.sensor.disable(self.sensor.GYRO_XYZ)

    def read(self):
        '''Returns (x_gyro, y_gyro, z_gyro) in units of degrees/sec'''
        rawVals = self.sensor.rawRead()[0:3]
        return tuple([ v*self.scale for v in rawVals ])

class KeypressSensor(SensorBase):
    svcUUID = UUID(0xFFE0)
    dataUUID = UUID(0xFFE1)
    ctrlUUID = None
    sensorOn = None

    def __init__(self, periph):
        SensorBase.__init__(self, periph)

    def enable(self):
        SensorBase.enable(self)
        self.char_descr = self.service.getDescriptors(forUUID=0x2902)[0]
        self.char_descr.write(struct.pack('<bb', 0x01, 0x00), True)

    def disable(self):
        self.char_descr.write(struct.pack('<bb', 0x00, 0x00), True)

class OpticalSensorOPT3001(SensorBase):
    svcUUID  = _TI_UUID(0xAA70)
    dataUUID = _TI_UUID(0xAA71)
    ctrlUUID = _TI_UUID(0xAA72)

    def __init__(self, periph):
       SensorBase.__init__(self, periph)

    def read(self):
        '''Returns value in lux'''
        raw = struct.unpack('<h', self.data.read()) [0]
        m = raw & 0xFFF;
        e = (raw & 0xF000) >> 12;
        return 0.01 * (m << e)

class BatterySensor(SensorBase):
    svcUUID  = UUID("0000180f-0000-1000-8000-00805f9b34fb")
    dataUUID = UUID("00002a19-0000-1000-8000-00805f9b34fb")
    ctrlUUID = None
    sensorOn = None

    def __init__(self, periph):
       SensorBase.__init__(self, periph)

    def read(self):
        '''Returns the battery level in percent'''
        val = ord(self.data.read())
        return val

class KeypressDelegate(DefaultDelegate):
    BUTTON_L = 0x02
    BUTTON_R = 0x01
    ALL_BUTTONS = (BUTTON_L | BUTTON_R)

    _button_desc = {
        BUTTON_L : "Left button",
        BUTTON_R : "Right button",
        ALL_BUTTONS : "Both buttons"
    }

    def __init__(self):
        DefaultDelegate.__init__(self)
        self.lastVal = 0

    def handleNotification(self, hnd, data):
        # NB: only one source of notifications at present
        # so we can ignore 'hnd'.
        val = struct.unpack("B", data)[0]
        down = (val & ~self.lastVal) & self.ALL_BUTTONS
        if down != 0:
            self.onButtonDown(down)
        up = (~val & self.lastVal) & self.ALL_BUTTONS
        if up != 0:
            self.onButtonUp(up)
        self.lastVal = val

    def onButtonUp(self, but):
        print ( "** " + self._button_desc[but] + " UP")

    def onButtonDown(self, but):
        print ( "** " + self._button_desc[but] + " DOWN")


class SensorTag(Peripheral):
    def __init__(self,addr,version=AUTODETECT):
        Peripheral.__init__(self,addr)
        if version==AUTODETECT:
            svcs = self.discoverServices()
            if _TI_UUID(0xAA70) in svcs:
                version = SENSORTAG_2650
        if version==SENSORTAG_2650:
            self._mpu9250 = MovementSensorMPU9250(self)
            self.IRtemperature = IRTemperatureSensorTMP007(self)
            self.accelerometer = AccelerometerSensorMPU9250(self._mpu9250)
            self.humidity = HumiditySensorHDC1000(self)
            self.magnetometer = MagnetometerSensorMPU9250(self._mpu9250)
            self.barometer = BarometerSensorBMP280(self)
            self.gyroscope = GyroscopeSensorMPU9250(self._mpu9250)
            self.keypress = KeypressSensor(self)
            self.lightmeter = OpticalSensorOPT3001(self)
            self.battery = BatterySensor(self)

def main():
    import time
    import sys
    import socket

    IP   = '39.106.2.56'
    PORT = 9003

    
    # 24:71:89:D0:65:02
    print('Connecting to 24:71:89:D0:65:02')
    tag = SensorTag('24:71:89:CC:3D:83')

    # Enabling selected sensors
    tag.IRtemperature.enable()
    tag.humidity.enable()
    tag.barometer.enable()
    tag.accelerometer.enable()
    tag.magnetometer.enable()
    tag.gyroscope.enable()
    tag.battery.enable()
    tag.keypress.enable()
    tag.setDelegate(KeypressDelegate())
    tag.lightmeter.enable()

    # Some sensors (e.g., temperature, accelerometer) need some time for initialization.
    # Not waiting here after enabling a sensor, the first read value might be empty or incorrect.

    #Connect socket server
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        s.connect((IP,PORT))
    except Exception as e:
        print('server not find or not open')
        sys.exit()

    time.sleep(1.0)
   
    while True:
        temp   = format("%.2f"%tag.IRtemperature.read()[1])
        #print(temp)
        #print(format("%.2f"%tag.IRtemperature.read()[0]))
        humi   = format("%.2f"%tag.humidity.read()[1])
        baro   = format("%.2f"%tag.barometer.read()[1])
        light  = format("%d"%tag.lightmeter.read()) 
        gyro_x = format("%.2f"%tag.gyroscope.read()[0])
        gyro_y = format("%.2f"%tag.gyroscope.read()[1])
        gyro_z = format("%.2f"%tag.gyroscope.read()[2])
        acc_x  = format("%.2f"%tag.accelerometer.read()[0])
        acc_y  = format("%.2f"%tag.accelerometer.read()[1])
        acc_z  = format("%.2f"%tag.accelerometer.read()[2])
        mag_x  = format("%.2f"%tag.magnetometer.read()[0])
        mag_y  = format("%.2f"%tag.magnetometer.read()[1])
        mag_z  = format("%.2f"%tag.magnetometer.read()[2])
       


        data_json = {
        "sensortag":{
            "deviceId":"123456",
            "temp":temp,
            "humi":humi,
            "baro":baro,
            "light":light,
            "gyro":{
                "x":gyro_x,"y":gyro_y,"z":gyro_z
                },
            "acc":{
                "x":acc_x,"y":acc_y,"z":acc_z
                },
            "mag":{
                "x":mag_x,"y":mag_y,"z":mag_z
                }
            }
        }
        a = json.dumps(data_json)
        s.sendall(a.encode())
        print(data_json)
        localtime = time.asctime(time.localtime(time.time()))
        print( "Time:",localtime,"\n")
        #s.sendall(te.encode())

    tag.disconnect()
    del tag
    
if __name__ == "__main__":
    main()
