package com.mumuWeibo2;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeiboListAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<WeiboInfo> weiboList;
	private AsyncBitmapLoader asyncBitmapLoader=null;
	//private WeiboGetPicAsyncTask task=null;
	
	public WeiboListAdapter(Context context,ArrayList<WeiboInfo> list)
	{
		this.context=context;
		weiboList=list;
		asyncBitmapLoader=new AsyncBitmapLoader();
	}
		

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return weiboList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null)
		{
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.weibo_list_item,null);
			holder.userProfileImage =(ImageView)convertView.findViewById(R.id.user_profile_image);
			holder.userName=(TextView)convertView.findViewById(R.id.user_screen_name);
			holder.createTime=(TextView)convertView.findViewById(R.id.create_time_inlist);
			holder.oneWeibo=(OneWeiboView)convertView.findViewById(R.id.one_weibo_view_list);			
			convertView.setTag(holder);
		}
		else
			holder=(ViewHolder) convertView.getTag();
			
		WeiboInfo weiboInfo=new WeiboInfo();
		weiboInfo=(WeiboInfo)weiboList.get(position);	
		
		//如果该微博已被删除
		if(weiboInfo.isDeleted().equals("1"))
		{
			holder.userProfileImage.setImageResource(R.drawable.defalut_profile_image);
			holder.userName.setText("");
			holder.createTime.setText(MumuWeiboUtility.parseWeiboTime(weiboInfo.getCreateTime()));			
			holder.oneWeibo.setView(weiboInfo);
			
			return convertView;
		}			
		
		final WeiboUserInfo user=weiboInfo.getWeiboUser();
		//if(user==null)return convertView;
		asyncBitmapLoader.loadBitmap(holder.userProfileImage,MumuWeiboUtility.IMAGE_TYPE.PROFILE,user.getProfile());			
		
		String s=user.getName();
		//final String Username0=s;
		
	
		//if(s.getBytes().length>24)			
		//	s=user.getName().substring(0,7)+"...";
					
		holder.userName.setText(s);
		holder.createTime.setText(MumuWeiboUtility.parseWeiboTime(weiboInfo.getCreateTime()));		
		holder.oneWeibo.setView(weiboInfo);		
		
		//点击事件		
				OnClickListener lis=new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(WeiboListAdapter.this.context, "show user info", Toast.LENGTH_LONG).show();
						
						Intent intent=new Intent();
				    	intent.setClass(context, UserInfoShow.class);				    	   	
				    	intent.putExtra("userinfo", user);
				    	context.startActivity(intent);
					}			
				};
				
				holder.userProfileImage.setOnClickListener(lis);
				holder.userName.setOnClickListener(lis);
		
		return convertView;	
	}
	
	
	private class ViewHolder{		
		public ImageView userProfileImage ;
		public TextView userName;		
		public OneWeiboView oneWeibo;
		public TextView createTime;		
		public ViewHolder(){
			
		}
		
	}
}
