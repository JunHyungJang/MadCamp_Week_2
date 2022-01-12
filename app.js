

// 내가 적은 코드

//Node module Loading
const express = require('express');
const mysql = require('mysql');
const path = require('path');
const morgan = require('morgan');
const bodyParser = require('body-parser');
const router = require('./routes');


// const cors = require('cors');


var app = express();

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var usrappendRouter = require('./routes/usrappend');


app.use('/',indexRouter);
app.use('/users',usersRouter);
app.use('/usrappend', usrappendRouter);

app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);


const con = mysql.createConnection({
  host : 'localhost',
  user : 'root',
  port : 3306,
  password : '12345',
  database : 'new_table'
});

//application/x-www form urlencoded 방식의 content type 데이터를 받음
app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());
// app.use(morgan('dev'));
// app.use(cors());

const port = process.env.PORT || 80;
/////////////////

con.connect();
console.log("ok");


///router 몰라서 여기에 다 써버림

//모든 데이터 가져오기
app.get('/name',(req,res,next)=>{
  console.log("name");
  con.query('SELECT * FROM new_person', function(error,result,fields){
      // connection.on('error',function(err){
      //     console.log('[MYSQL]ERROR',err);
      // });
      console.log(result);
      if(result){
          res.end(JSON.stringify(result));
      }
      else
      {
          res.end(JSON.stringify('No person here'));
      }
  });
});

// //data 넣기

// var sql_insert = 'INSERT INTO new_person (name,sex,img) VALUES(?,?,?)';
// var params_insert = ['kim','man',''];

// app.get('/insert',(req,res,next)=>{
//   con.query(sql_insert,params_insert,function(error,result,){
//     if(error) console.log(error);
//     console.log('one inserted');
// })})


//data individual 넣기
app.get('/insert/:name/:sex/:img',async (req,res) =>{
  
  var sql_insert = 'INSERT INTO new_person (name,sex,img) VALUES(?,?,?)';
  var sql_name = req.params.name;
  var sql_sex = req.params.sex;
  var sql_img = req.params.img;
  var params_insert = [sql_name,sql_sex,sql_img]
  
  con.query(sql_insert,params_insert,(error,result)=>{
    if(error) console.log(error)
    // res.end(result)
    console.log('individual inserted')
  })
})


//worldcup algorithm
app.get('/worldcup/:sex/:number',(req,res,next)=>{
  var sql_sex = req.params.sex;
  var sql_number = parseInt(req.params.number)
  var sql = 'SELECT img FROM new_person where sex = ? order by rand() limit ?'
  var params_insert = [sql_sex,sql_number];
  console.log('worldup')
  con.query(sql,params_insert,(error,result)=>{
    console.log(result);
    if(result){
      // const obj = JSON.parse(result);
      // console.log(obj.img)
      // revised = JSON.stringify(result);
      console.log('df')
      console.log(result[0].img);
      console.log('asdf')
      var arr = [];
      for (var i = 0 in result){
        arr.push(result[i].img)
      }
      console.log(JSON.stringify(arr));
      // res.end(JSON.stringify(result));
      res.end(JSON.stringify(arr))
      // res.send(obg.img);
    }
    else{
      res.end(JSON.stringify('No person here'));
    }
  })
})

var sql_delete = 'DELETE FROM new_person where id = ?';
var params_delete = [3];

app.get('/delete',(req,res,next)=>{
  con.query(sql_delete,params_delete,function(error,result,){
    if(error) {
      console.log(error);
    }
    else {
      console.log('one deleted');
      // res.end(JSON.stringify(result));
      console.log(result)
  }
    
  })
})

//인자로 받아서 보여주기

var fs = require('fs');


app.get('/user/:id',async (req,res) =>{

  var sql_id = 'SELECT * FROM person where id = ?'
  var params_id = req.params.id;
  con.query(sql_id,params_id,(error,result)=>{
    if(error) console.log(error)
    res.end(JSON.stringify(result[0]));
    console.log(result);
  })
})

const testFolder = './Images/';
// const fs = require('fs'); count = 0;

fs.readdir(testFolder, (err, files) => {

  files.forEach(file => {
  app.get('/'+file, async(req,res) => {
    fs.readFile('./Images/'+file,function(err,data){
      // res.writeHead(200, {"Content-Type": "image/png"});
      res.write(data)
      res.end();
    })
  })
  });
});



app.get('/hello', async(req,res) => {
  fs.readFile('./Images/1641739268420',function(err,data){
    // res.writeHead(200, {"Content-Type": "image/png"});
    res.write(data)
    res.end();
  })
})



const multer = require('multer');
const { randomInt } = require('crypto');
var pth = "";

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
      cb(null, './Images')
  },
  filename: (req, file, cb) => {
    pth =  Date.now() + path.extname(file.originalname)
    cb(null,pth);
  
  }
})

const upload = multer({storage: storage});


app.set("View engine","ejs");

app.get("/upload",(req,res) => {
  res.render("upload");
});

app.post("/upload",upload.fields([{
  name: 'files', maxCount:1
},{
  name: 'name', maxCount:1
},{
  name: 'sex', maxCount:1
}
]), (req,res) => {

  var sql_insert = 'INSERT INTO new_person (name,sex,img) VALUES(?,?,?)';
  var params_insert = [req.body.name,req.body.sex,pth];
  con.query(sql_insert,params_insert,(error,result)=>{
    if(error) console.log(error)
    // res.end(result)
    console.log('individual inserted')
    
    const testFolder = './Images/';
    
    fs.readdir(testFolder, (err, files) => {

      files.forEach(file => {
      app.get('/'+file, async(req,res) => {
        fs.readFile('./Images/'+file,function(err,data){
          // res.writeHead(200, {"Content-Type": "image/png"});
          res.write(data)
          res.end();
        })
      })
      });
    });
    // app('/'+pth,as)
  })

});



app.listen(80,()=> {
  console.log('hello');
})


module.export = app;


