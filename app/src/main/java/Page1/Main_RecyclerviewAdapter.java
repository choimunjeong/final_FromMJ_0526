package Page1;


import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hansol.spot_200510_hs.R;

import java.util.ArrayList;
import java.util.List;


public class Main_RecyclerviewAdapter extends  RecyclerView.Adapter<Main_RecyclerviewAdapter.ViewHolder> {

    //리아시클러뷰 안 리사이클러뷰 관련
    Second_RecyclerviewAdapater adapter;
    private List<String> real_items = new ArrayList<String>();

    // adapter에 들어갈 list 입니다.
    private ArrayList<String> listData;
    private Context context;

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private boolean isFirst = true;


    public Main_RecyclerviewAdapter(ArrayList<String> data, Context context){
        this.listData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_main_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        //순서먼저 검색
        if(listData.get(position) == "0"){
            //첫번째만 펼쳐져 있기 위함
            if(isFirst) {
                if (position == 0) {
                    selectedItems.put(position, true);
                    prePosition = position;
                    isFirst = false;
                }
            }
            viewHolder.icon.setBackgroundResource(R.drawable.ic_icon_my);
            viewHolder.textView1.setText("MY메뉴");
            real_items.clear();
            real_items.add( "나의 여행 기록");
            real_items.add( "여행유형 테스트");
            adapter = new Second_RecyclerviewAdapater(context, real_items);
            viewHolder.recyclerView.setLayoutManager( new LinearLayoutManager(context));
            viewHolder.recyclerView.setAdapter(adapter);
        }

        else if(listData.get(position) == "1"){
            viewHolder.icon.setBackgroundResource(R.drawable.ic_icon_city);
            viewHolder.textView1.setText("여행지 탐색하기");
            real_items.clear();
            real_items.add( "여행 카테고리 보기");
            real_items.add( "도시 검색하기");
            real_items.add( "내가 찜한 관광지");
            adapter = new Second_RecyclerviewAdapater(context, real_items);
            viewHolder.recyclerView.setLayoutManager( new LinearLayoutManager(context));
            viewHolder.recyclerView.setAdapter(adapter);
        }

        else if(listData.get(position) == "2"){
            viewHolder.icon.setBackgroundResource(R.drawable.ic_icon_train);
            viewHolder.textView1.setText("여행 계획하기");
            real_items.clear();
            real_items.add( "기차 스케쥴짜기");
            real_items.add( "일정 등록하기");
            adapter = new Second_RecyclerviewAdapater(context, real_items);
            viewHolder.recyclerView.setLayoutManager( new LinearLayoutManager(context));
            viewHolder.recyclerView.setAdapter(adapter);
        }

        //리사이클러뷰 넣는 부분
        viewHolder.onBind(position);
        viewHolder.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItems.get(position)) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(position);
                } else {
                    // 직전의 클릭됐던 Item의 클릭상태를 지움
                    selectedItems.delete(prePosition);
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(position, true);
                }
                // 해당 포지션의 변화를 알림
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                // 클릭된 position 저장
                prePosition = position;
            }
        });
    }



    public int getItemCount() { return listData.size(); }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView textView1;
        private Button expand_btn;
        private RecyclerView recyclerView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.menu_header_img);
            textView1 = itemView.findViewById(R.id.menu_title);
            expand_btn = itemView.findViewById(R.id.menu_expand_btn);
            recyclerView = itemView.findViewById(R.id.second_recyclerview);
        }


        void onBind(int position) {
            this.position = position;
            changeVisibility(selectedItems.get(position));
        }


        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 40*real_items.size();
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    recyclerView.getLayoutParams().height = value;
                    recyclerView.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }



    }
}
