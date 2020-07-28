## 카카오 맵 API 샘플 코드   

#### 출처 : https://apis.map.kakao.com/   
#### 사용자 : 한국산업기술대학교 2016156026 학번 소프트웨어공학과 이형석   
#### 구성   
		* DemoListAdapter.java   
		* DemoListItem.java   
		* MainActivity.java   
		* MapApiConst.java   

#### DemoListAdapter.java -> DemoListItem.java   

```java   
// DemoListAdapter.java

public class DemoListAdapter extends ArrayAdapter<DemoListItem> {

    public DemoListAdapter(Context context, DemoListItem[] demos) {
        super(context, R.layout.demo_list_item_view, demos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DemoListItem demo = getItem(position);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.demo_list_item_view, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(demo.titleId);

        TextView description = (TextView) convertView.findViewById(R.id.description);
        description.setText(demo.descriptionId);

        return convertView;
    }
}
```   

```java
// DemoListItem.java
public class DemoListItem {
	public final int titleId;
	public final int descriptionId;
	public final Class<? extends FragmentActivity> activity;

	// 아이템 속성 설정 
	public DemoListItem(int titleId, int descriptionId,
			Class<? extends FragmentActivity> activity) {
		this.titleId = titleId;
		this.descriptionId = descriptionId;
		this.activity = activity;
	}

	// item list화 
	public static final DemoListItem[] DEMO_LIST_ITEMS = {
			new DemoListItem(R.string.map_view_demo_title,
					R.string.map_view_demo_desc, MapViewDemoActivity.class),
			new DemoListItem(R.string.marker_demo_title,
					R.string.marker_demo_desc, MarkerDemoActivity.class),
			new DemoListItem(R.string.polygon_demo_title,
					R.string.polygon_demo_desc, PolygonDemoActivity.class),
			new DemoListItem(R.string.location_demo_title,
					R.string.location_demo_desc, LocationDemoActivity.class),
			new DemoListItem(R.string.camera_demo_title,
					R.string.camera_demo_desc, CameraDemoActivity.class),
			new DemoListItem(R.string.events_demo_title,
					R.string.events_demo_desc, EventsDemoActivity.class)
	};
}
```   

#### MainActivity.java   

```java
public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListAdapter adapter = new DemoListAdapter(this, DemoListItem.DEMO_LIST_ITEMS);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoListItem demo = (DemoListItem) getListAdapter().getItem(position);
        startActivity(new Intent(this, demo.activity));
    }

}
```   

#### MapApiConst.java

```java
public class MapApiConst {
	// http://developers.daum.net/console
	// Native App key 등록 
    public static final String DAUM_MAPS_ANDROID_APP_API_KEY = "Your Native App key";
}
```