package com.example.booklist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklist.data.BookFragmentAdapter;
import com.example.booklist.data.BookListFragment;
import com.example.booklist.data.model.Book;
import com.example.booklist.data.BookSaver;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_ADDNEW = CONTEXT_MENU_DELETE+1;
    private static final int CONTEXT_MENU_UPDATE =  CONTEXT_MENU_ADDNEW+1;
    public static final int CONTEXT_MENU_ABOUT =CONTEXT_MENU_UPDATE+1;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    public static final int REQUEST_CODE_UPDATE_BOOK =902;

    private List<Book> listBooks=new ArrayList<>();
    private BookSaver bookSaver;
    BookAdapter bookAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookSaver.save();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        bookSaver=new BookSaver(this);
        listBooks=bookSaver.load();
        if(listBooks.size()==0)
            init();
        bookAdapter=new BookListMainActivity.BookAdapter(BookListMainActivity.this,R.layout.list_view_item_book,listBooks);

        BookFragmentAdapter myPageAdapter=new BookFragmentAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas=new ArrayList<Fragment>();
        datas.add(new BookListFragment(bookAdapter));
        myPageAdapter.setData(datas);

        ArrayList<String> titles=new ArrayList<String>();
        titles.add("A");
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout=findViewById(R.id.tablayout);
        ViewPager viewPager=findViewById(R.id.viewPager);
        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==findViewById(R.id.list_view_books)) {
            super.onCreateContextMenu(menu, v, menuInfo);
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(listBooks.get(info.position).getTitle());
            //设置内容 参数1为分组，参数2对应条目的id，参数3是指排列顺序，默认排列即可
            menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_ADDNEW, 0, "添加");
            menu.add(0, CONTEXT_MENU_UPDATE, 0, "修改");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "关于...");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE:
                final int deletePosition=((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("询问")
                        .setMessage("你确定要删除这条吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listBooks.remove(deletePosition);
                                bookAdapter.notifyDataSetChanged();
                                Toast.makeText(BookListMainActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                            }})
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }})
                        .create().show();
                break;
            case CONTEXT_MENU_ADDNEW:
                Intent intent1 = new Intent(this,NewBookActivity.class);
                intent1.putExtra("title","无名书籍");
                intent1.putExtra("price","0.0");
                intent1.putExtra("insert_position",((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                startActivityForResult(intent1, REQUEST_CODE_NEW_BOOK);

                break;
            case CONTEXT_MENU_UPDATE:
                int position=((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Intent intent2 = new Intent(this,NewBookActivity.class);
                intent2.putExtra("title",listBooks.get(position).getTitle());
                intent2.putExtra("price",listBooks.get(position).getPrice());
                intent2.putExtra("insert_position",position);
                startActivityForResult(intent2, REQUEST_CODE_UPDATE_BOOK);

                break;
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(BookListMainActivity.this,"关于",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_NEW_BOOK:
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra("title");
                    int insertPosition = data.getIntExtra("insert_position", 0);
                    double price=data.getDoubleExtra("price",0);
                    getListBooks().add(insertPosition, new Book(title, R.drawable.book_no_name,price));
                    bookAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_CODE_UPDATE_BOOK:
                if(resultCode==RESULT_OK)
                {
                    String title = data.getStringExtra("title");
                    int position = data.getIntExtra("insert_position", 0);
                    double price=data.getDoubleExtra("price",0);
                    getListBooks().get(position).setTitle(title);
                    getListBooks().get(position).setPrice(price);
                    bookAdapter.notifyDataSetChanged();
                }
        }
    }

    public List<Book> getListBooks(){
        return listBooks;
    }

    private void init() {
        listBooks.add(new Book("软件项目管理案例教程（第4版）",R.drawable.book_2,10.0));
        listBooks.add(new Book("创新工程实践",R.drawable.book_no_name,20.0));
        listBooks.add(new Book("信息安全数学基础（第2版）",R.drawable.book_1,30.0));
    }

    public class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        public BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getTitle()+" , "+book.getPrice()+"元");
            return view;
        }
    }
}
