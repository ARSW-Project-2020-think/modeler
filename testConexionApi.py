from requests import post,get
"""print("Localhost")
z = post("http://localhost:8080/user/login",data='{"username":"jay@mail.com"}',headers={"Content-Type":"application/json"})
print(z)
w = z.json()
print(w)
print(post("http://localhost:8080/api",headers={"Authorization":w["token"]}).text)"""
print("heroku")
z = post("http://localhost:8080/user/login",data='{"username":"jay@mail.com","password":"test"}',headers={"Content-Type":"application/json"})
print(z)
w = z.json()
print(w)
print(get("http://localhost:8080/user/data",headers={"Authorization":w["token"]}).text)
