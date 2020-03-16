from requests import post,get
"""print("Localhost")
z = post("http://localhost:8080/user/login",data='{"username":"jay@mail.com"}',headers={"Content-Type":"application/json"})
print(z)
w = z.json()
print(w)
print(post("http://localhost:8080/api",headers={"Authorization":w["token"]}).text)"""
print("heroku")
z = post("https://class-modeler.herokuapp.com/user/login",data='{"username":"jay@mail.com","password":"test"}',headers={"Content-Type":"application/json"})
print(z)
w = z.json()
print(w)
print(get("https://class-modeler.herokuapp.com/user/data",headers={"Authorization":w["token"]}).text)
