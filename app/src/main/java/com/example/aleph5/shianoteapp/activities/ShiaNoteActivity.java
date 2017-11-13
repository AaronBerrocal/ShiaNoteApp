package com.example.aleph5.shianoteapp.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.aleph5.shianoteapp.R;
import com.example.aleph5.shianoteapp.adapters.NoteAdapter;
import com.example.aleph5.shianoteapp.models.Board;
import com.example.aleph5.shianoteapp.models.Note;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class ShiaNoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {

    private ListView listView;
    private FloatingActionButton fab;

    private NoteAdapter adapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardId;
    private Board board;

    //MODIFICATION ALERT
    private  String enterDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shia_note);

        realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null){
            boardId = getIntent().getExtras().getInt("id");
            //MODIFICATION ALERT
            enterDescription = getIntent().getExtras().getString("description");
            //
        }

        board = realm.where(Board.class).equalTo("id", boardId).findFirst();
        board.addChangeListener(this);
        notes = board.getNotes();

        this.setTitle(board.getShortInfo());

        listView = findViewById(R.id.listViewNote);
        adapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);
        listView.setAdapter(adapter);

        //FAB LOGIC HERE

        if(notes.size() < 1){
            createNewNote(enterDescription);
        }

    }

    private void createNewNote(String note){
        realm.beginTransaction();
        Note _note = new Note(note);
        realm.copyToRealm(_note);
        board.getNotes().add(_note);
        realm.commitTransaction();
    }

    @Override
    public void onChange(Board board) {
        adapter.notifyDataSetChanged();
    }
}
