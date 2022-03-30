# Android_My_cms
만든 목적 : ExoPlayer, Glide, Retrofit2에 대하여 학습하고 cms에 대하여 배움과 동시에 라이브러리들을 활용해보기 위함

만들면서 힘들었던 점 : Rest API의 명세에 맞게 하였으나 JSON의 mp4 파일을 exoplayer에 적용시키는 것에 난항을 겪음. 

	
미비했던 부분이나 아쉬운 점 : Retrofit2라는 라이브러리를 제대로 활용하지 못하여 아쉬웠다. 
AsyncTask의 비동기식을 이용하여 다시 한번 해봐야겠다. 
RecyeclerView를 통하여 ImageList를 표현하였는데 ImageList의 item들을 onClick Event를 통하여 항목에 맞는 동영상을
같은 Activity 내에서 재생시키고자 하였으나 Click Event에 난항을 겪어 같은 Activity 내에서 재생시키지 못하여 Intent로
다른 Activity에서 처리하였던 것이 아쉬웠다
                         
앞으로 더 추가해야할 사항이나 수정 사항 : MVP 패턴이나 MVVM 패턴을 활용하여 제대로 된 cms를 만들어보고 싶음                           


<수정 사항> 
Fragment 추가로 인한 , SecondActivity를 삭제하였고, 같은 Activity내에서 동영상을 재생시키는 것에 성공하였다.

<추가 사항>
동영상의 위치를 나타내는 ProgressBar 기능 추가
