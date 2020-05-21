from firebase import firebase
from tensorflow.keras.models import load_model
import numpy as np
from sklearn.preprocessing import StandardScaler
import joblib

odscaler=joblib.load(r'C:\Users\Aryamaan\Desktop\PROJECTS\Store-App\odscaler.bin')

firebase=firebase.FirebaseApplication("https://shoppingnew-58d41.firebaseio.com/")

model=load_model(r"C:\Users\Aryamaan\Desktop\PROJECTS\Store-App\model_h")
val=[]
while(True):
	val=[]
	if(firebase.get("Nilgiris/ByHour/new",None)==0.0):
		for i in range(1,21):
			val.append(firebase.get("Nilgiris/ByHour/val"+str(i),None))
		valpred=np.array(val).reshape(1,20,1)
		pred=float(model.predict(valpred)[0,0])
		pred=odscaler.inverse_transform([pred])[0]
		firebase.put("Nilgiris/ByHour","pred1",float(int(pred)))
		val2=np.roll(val,-1)
		val2[-1]=pred
		val2pred=val2.reshape(1,20,1)
		pred2=float(model.predict(val2pred)[0,0])
		pred2=odscaler.inverse_transform([pred2])[0]
		firebase.put("Nilgiris/ByHour","pred2",float(int(pred2)))
		val3=np.roll(val2,-1)
		val3[-1]=pred2
		val3pred=val3.reshape(1,20,1)
		pred3=float(model.predict(val3pred)[0,0])
		pred3=odscaler.inverse_transform([pred3])[0]
		firebase.put("Nilgiris/ByHour","pred3",float(int(pred3)))
	else:
		for i in range(2,21):
			val.append(firebase.get("Nilgiris/ByHour/val"+str(i),None))
		val.append(firebase.get("Nilgiris/ByHour/new",None))
		valpred=np.array(val).reshape(1,20,1)
		model=load_model(r"C:\Users\Aryamaan\Desktop\PROJECTS\Store-App\model_h")
		pred=float(model.predict(valpred)[0,0])
		pred=odscaler.inverse_transform([pred])[0]
		firebase.put("Nilgiris/ByHour","pred1",float(int(pred)))
		val2=np.roll(val,-1)
		val2[-1]=pred
		val2pred=val2.reshape(1,20,1)
		pred2=float(model.predict(val2pred)[0,0])
		pred2=odscaler.inverse_transform([pred2])[0]
		firebase.put("Nilgiris/ByHour","pred2",float(int(pred2)))
		val3=np.roll(val2,-1)
		val3[-1]=pred2
		val3pred=val3.reshape(1,20,1)
		pred3=float(model.predict(val3pred)[0,0])
		pred3=odscaler.inverse_transform([pred3])[0]
		firebase.put("Nilgiris/ByHour","pred3",float(int(pred3)))
		for i in range(2,21):
			valc=firebase.get("Nilgiris/ByHour/val"+str(i),None)
			firebase.put("Nilgiris/ByHour","val"+str(i-1),valc)
		n=np.array([firebase.get("Nilgiris/ByHour/new",None)]).reshape(-1,1)
		n=odscaler.fit_transform(n)[0]
		firebase.put("Nilgiris/ByHour","val"+str(20),float(n))
		firebase.put("Nilgiris/ByHour","new",0.0)
		
		
		

