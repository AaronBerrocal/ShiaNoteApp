package com.example.aleph5.shianoteapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aleph5.shianoteapp.R;
import com.example.aleph5.shianoteapp.adapters.BoardAdapter;
import com.example.aleph5.shianoteapp.models.Board;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ShiaBoardActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>, AdapterView.OnItemClickListener {

    private Realm realm;

    private FloatingActionButton fab;
    private ListView listView;
    private BoardAdapter adapter;

    private RealmResults<Board> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shia_board);

        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        boards.addChangeListener(this);

        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView = findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        //FAB LOGIC HERE

        registerForContextMenu(listView);
    }

    //** CRUDE methods **//

    private void createNewBoard(String boardName){
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();
    }

    private void editBoard(String newName, Board board){
        realm.beginTransaction();
        board.setShortInfo(newName);
        realm.copyToRealmOrUpdate(board);
        realm.commitTransaction();
    }

    private void deleteBoard(Board board){
        realm.beginTransaction();
        board.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAll(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    /* EVENTS */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.delete_all:
                deleteAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(boards.get(info.position).getShortInfo());
        getMenuInflater().inflate(R.menu.context_menu_board_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){

            case R.id.delete_board:
                deleteBoard(boards.get(info.position));
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ShiaBoardActivity.this, ShiaNoteActivity.class);
        intent.putExtra("id", boards.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onChange(RealmResults<Board> boards) {
        adapter.notifyDataSetChanged();
    }
}
