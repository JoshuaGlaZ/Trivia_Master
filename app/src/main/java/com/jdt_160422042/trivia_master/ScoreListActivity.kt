package com.jdt_160422042.trivia_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdt_160422042.trivia_master.databinding.ActivityScoreListBinding
import org.json.JSONObject

class ScoreListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreListBinding
    var players:ArrayList<Player> = ArrayList()

    companion object {
        val PLAYERS_KEY = "player_name"
    }

    fun addPlayer(playerName:String, score: String, difficulty: String, type: String){
        val url = "http://10.0.2.2/trivia_master/add_player.php"
        val q = Volley.newRequestQueue(this)
        val sr = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("volleycek", it)
                getPlayerData()
            },
            { Log.e("volleycek", it.message.toString()) }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = playerName
                params["score"] = score
                params["difficulty"] = difficulty
                params["type"] = type
                return params
            }
        }
        q.add(sr)
    }

    fun getPlayerData(){
        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/trivia_master/get_players.php"
        val sr = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Player>>() {}.type

                    players = Gson().fromJson(data.toString(), sType) as ArrayList<Player>

                    Log.d("apiresult", players.toString())
                    updateList()
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        )
        q.add(sr)
    }

    fun updateList() {
        val lm = LinearLayoutManager(this)
        with(binding.recyclerView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = ScoreAdapter(players)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerName: String? = intent.getStringExtra(GameSetupActivity.PLAYER_NAME_KEY)
        val difficulty: String? = intent.getStringExtra(GameSetupActivity.DIFFICULTY_KEY)
        val type: String? = intent.getStringExtra(GameSetupActivity.TYPE_KEY)
        val score = intent.getIntExtra(GameActivity.SCORE_KEY, 0)

        addPlayer(playerName!!, score.toString(), difficulty.toString(), type.toString())
//
//        val sharedFile = "com.jdt_160422042.trivia_master"
//        val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
//        val playersJson = shared.getString(PLAYERS_KEY, null)
//        var players = arrayListOf<Player>()
//        val gson = Gson()
//
//        if (playersJson != null) {
//            val type = object : TypeToken<ArrayList<Player>>() {}.type
//            players = gson.fromJson(playersJson, type)
//        }

//        val player = Player(playerName, score, difficulty, type)
//        players.add(player)

//        val savePlayers: String = gson.toJson(players)
//        shared.edit().putString(PLAYERS_KEY, savePlayers).apply()

//        val lm = LinearLayoutManager(this)
//        with(binding.recyclerView) {
//            layoutManager = lm
//            setHasFixedSize(true)
//            adapter = ScoreAdapter(players)
//        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, GameSetupActivity::class.java)
            intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, playerName)
            intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty)
            intent.putExtra(GameSetupActivity.TYPE_KEY, type)
            startActivity(intent)
        }
    }
}