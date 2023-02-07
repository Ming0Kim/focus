const express = require('express')
const jwt = require('jsonwebtoken')
const cors = require('cors')
const bodyParser = require('body-parser')
const fs = require('fs')
const notices = require('./db/notices.json')

const port = 3000
const app = express()

app.use(cors())
app.use(bodyParser.json())

app.get('/', (req, res) => {
  res.json({
    message: 'API LOGIN TEST SERVER'
  })
})

app.post('/login', (req, res) => {
  const userDB = fs.readFileSync("./db/user.json")
  const usersInfo = JSON.parse(userDB)
  const userIndex = usersInfo.users.findIndex((element) => element.loginId === req.body.loginId)
  // console.log(userIndex)
  if (
    req.body &&
    req.body.loginId === usersInfo.users[userIndex].loginId && 
    req.body.loginPw === usersInfo.users[userIndex].loginPw
  ) {
    const userInfo = usersInfo.users[userIndex]
    const token = jwt.sign(
      { userInfo }, "the_secret_key"
    )
    // console.log(res.body)
    res.json({
      token,
      loginId: userInfo.loginId,
      name: userInfo.name
    })
  } else {
    res.status(401).json({ error: 'Invalid login. Please try again.' })
  }
})

function verifyToken(req, res, next) {
  const bearerHeader = req.headers['authorization']
  console.log(bearerHeader)

  if (typeof bearerHeader !== 'undefined') {
    const bearer = bearerHeader.split(" ")
    const bearerToken = bearer[1]
    req.token = bearerToken
    next()
  } else {
    res.sendStatus(401)
  }
}

app.get('/notices', verifyToken, (req, res) => {
  console.log(req.token)
  jwt.verify(req.token, "the_secret_key", err => {
    if (err) {
      res.sendStatus(401)
    } else {
      // console.log(res)
      res.json({
        notices: notices
      })
    }
  })
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
