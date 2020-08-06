## QR_Code 스캔   

#### 출처 : https://superwony.tistory.com/78   

#### build.gradle(App level) 설정   

```kotlin
    // 추가 
    //noinspection GradleCompatible
    implementation 'com.android.support:appcompat-v7:27.1.1'
    implementation 'com.google.zxing:core:3.3.3'
    implementation ('com.journeyapps:zxing-android-embedded:3.6.0'){transitive=false}
```   

#### build.gradle(Project level) 설정   

```kotlin

// 추가 
repositories{
	jcenter()
}
```   

#### AndroidManifest 설정   

```kotlin
<application
	...
	android:hardwareAccelerated="true" // 추가

/>
```   

#### 바코드 스캔   

```kotlin
// kotlin

 var intentIntegrator:IntentIntegrator = IntentIntegrator(this)
 intentIntegrator.initiateScan()
```   

#### 데이터 추출   

```kotlin
// onCreate() 외부에 선언 

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if(result.contents == null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Scanned"+result.contents, Toast.LENGTH_SHORT).show()
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
```