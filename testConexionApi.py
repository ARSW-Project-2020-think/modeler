from requests import get, post
z = get("http://localhost:8080/user/login",data='{"username":"jay@mail.com"}',headers={"Content-Type":"application/json"})
w = z.json()
print(w)
print(get("http://localhost:8080/api",headers={"Authorization":"Bearer "+w["token"]}).text)
