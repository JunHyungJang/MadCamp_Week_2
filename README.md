# MadCamp_Week_2 - 이상형 월드컵
### 팀원
김진석, 장준형
## Abstract

자기가 찍은 사진이 이상형 월드컵에 등록되는 앱.
단순히 남자,여자 이상형 월드컵 뿐만 아니라 자신의 애완동물(ex 고양이, 개..)이나 사물까지
이상형 월드컵 database에 등록하여 색다른 이상형 월드컵을 할 수 있는 앱.
Mysql Database를 사용하여 유저의 이미지와 정보들을 저장함.

Tab1 : 카카오톡 로그인
Tab2 : 사진 업로딩
Tab3 : 이상형 월드컵

## TAB1 : Kakao Login

### Features

1. Kakao SDK를 이용한 로그인 
2. Login 화면 구현

### 1. Kakao SDK를 이용한 로그인

BeforeLogin.Java
```Java
public void onSessionOpened() {
                //로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback()
                {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //세션이 닫힘..
                        Toast.makeText(context, "세션이 닫혔습니다... 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        // 로그인 실패..
                        Toast.makeText(context, "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
```
카카오톡 SDK를 이용하여 사용자의 카카오톡 정보를 받아서 로그인 

AfterLogin.Java
```
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.afterlogin, container,false);
        context = getContext();

        strNick = getArguments().getString("name");
        strProfileImg = getArguments().getString("ProfileImage");
        strEmail = getArguments().getString("email");
 ```
 로그인 후 fragment 전환을 하면서 Account, name, Profile 을 받아서 AfterLogin.java로 값들을 넘겨준다.
 
 
 ### 2. 사진 업로딩
 갤러리에서 사진을 직접 가져오기, 카메라로 찍어서 사진을 가져오기 방식을 사용하여 Imagview에 사진을 로딩
 자신의 사진이 어떤 월드컵에 등록할지 고르는 버튼을 통해 사진을 등록
 
 이미지 형식을 bos 타입으로 바꾸고 send2server 함수로 file을 전달해줌
 ```Java
 public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            Bundle extras = data.getExtras();

            bitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            bitmapdata = bos.toByteArray();
            try {
                sendFile = new File(context.getCacheDir(),"image");
                sendFile.createNewFile();
                fos = new FileOutputStream(sendFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            btn_push.setEnabled(true);
            imageView.setImageBitmap(bitmap);

        }
        
        public void onClick(View view){
                if (arr[0] != null && arr[1]!= null) //arr[0] 계정, arr[1] type
                FileUploadUtils.send2Server(sendFile,arr[0],arr[1]);
 ```
 등록된 사진을 Bitmap 형식으로 바꾸고, bos type으로 바꾼 후에 
 File 형태로 이미지를 server에 전송함(Method by AsyncTask).
 
 서버에서 데이터를 받는 코드
 ```javascript
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
  })

});
```
사용자로부터 files, names, sex 3개의 인자를 받은 다음
query를 통해 mysql database에 저장함.

 ### 3. 이상형 월드컵
 
 이상형 월드컵의 종류와 몇 강으로 할 지 선택을 하면 server에서 database로 query를 보냄
 
 ```javascript
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

```
query를 받고 JSON 형식으로 받아온 후에 parsing 을 해서 지정된 type의 img개수 만큼 랜덤적으로
database에 뽑아온 후에 user app에 넘겨줌.

tournament_start.java
```Java
start_world_cup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int start_round = URL_list.size();
                if(start_round==16){
                    order = 0;
                }
                else if(start_round==8){
                    order = 8;
                }
                else if(start_round==4){
                    order = 12;
                }
                else{
                    order = 14;
                }
                bundle.putInt("order",order);
                bundle.putStringArrayList("URL_list",URL_list);
                Fragment fragment = new choose_one();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
  ```
  
  Worldcup 시작 버튼을 누르면, fragment간의 이동을 통해 정보를 전달함.
  
  ### 3. error handling 과 아쉬운 점
  
  #error handling
  fragment간에 화면을 전환하지 않고 정보를 전달하고 싶었는데 interface를 통한 listener을 설정하여 구현하였다.
  
  서버를 구축하는 과정에서 시간이 많이 들어 UI와 function들의 error-handling에 신경을 많이 쓰지 못하였다.
  Kakao계정 ID를 전역변수로 설정하여 자신이 업로드한 이미지는 월드컵에 나오지 않게 구현하려고 했는데
  시간적 여유가 없어서 구현하지 못하였다. 
  Dialog를 Listview 형태로 만들어 다양한 type의 월드컵을 구현하고 싶었지만 dataset이 많지가 않아서 
  male,female,cat 3개의 type으로 만들었다.
  
 
 
 
