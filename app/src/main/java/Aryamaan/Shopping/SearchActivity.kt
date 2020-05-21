package Aryamaan.Shopping

import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.product_row.*
import org.tensorflow.contrib.android.TensorFlowInferenceInterface
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SearchActivity : AppCompatActivity() {


    companion object {
        init {
            System.loadLibrary("tensorflow_inference")
        }
    }



    //ARRAY TO HOLD THE PREDICTIONS AND FLOAT VALUES TO HOLD THE IMAGE DATA

    var imageView: ImageView? = null
    var resultView: TextView? = null
    var update:Button?= null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val database=FirebaseDatabase.getInstance()
        val RootRef=database.getReference()
        //val HourRef=database.getReference("Nilgiris")

        RootRef.child("Nilgiris")
            .child("ByHour")
            .setValue(ByHour(val1=0.29069591
            ,val2=-0.56001764
            ,val3=-0.73016035
            ,val4=-0.73016035
            ,val5 = -0.73016035
            ,val6 = -0.73016035
            ,val7 = -0.73016035
            ,val8 = -0.73016035
            ,val9 = -0.73016035
            ,val10 = -0.73016035
            ,val11 = -0.73016035
            ,val12 = -0.73016035
            ,val13 = -0.73016035
            ,val14 = -0.73016035
            ,val15 = -0.25376076
            ,val16 =  1.07335237
            ,val17 =  0.69903841
            ,val18 =  1.31155216
            ,val19 =  0.49486716
            ,val20 =  0.97126675,
            new = 0.0,
            pred1 = 1.0,pred2 = 2.0,pred3 = 3.0
        ))

        RootRef.child("Nilgiris")
            .child("Products")
            .setValue(ProductList(wheat=0,
            rice=0))


        var products= arrayListOf<Product>()
        products.add(
            Product(
                storeName = "Nilgiris",
                current = 20,
                customerInHour1 = 0.0,
                customerInHour2 = 0.0,
                customerInHour3 = 0.0
            )
        )

        val hourListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val TAG = "MainActivity"

                    var hour = dataSnapshot.getValue(ByHour::class.java)
                    if (hour != null) {
                        products[0].customerInHour1 = hour.pred1
                        products[0].customerInHour2 = hour.pred2
                        products[0].customerInHour3 = hour.pred3
                        Log.d(TAG, "Valuex:" + hour.val1.toString())
                        recycler_view.apply() {
                            layoutManager = LinearLayoutManager(this@SearchActivity)
                            adapter = ProductsAdapter(products)
                        }
                        Log.d("SearchActivity","XXXX2")
                    }
                }

            }

        RootRef.child("Nilgiris").child("ByHour").addValueEventListener(hourListener)

        //WORKING UPTO HERE

        val productListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val TAG = "SearchActivity"

                val bundle: Bundle?= intent.extras
                val user_search = bundle?.get("search").toString()

                Log.d(TAG,"Checkpoint 1:"+user_search)

                for(snapshot in dataSnapshot.children){
                    Log.d(TAG, "Checkpoint 2:")
                    products[0].current = snapshot.child(user_search).getValue(Int::class.java)!!
                    Log.d(TAG, "Checkpoint 3:")
                    recycler_view.apply() {
                        layoutManager = LinearLayoutManager(this@SearchActivity)
                        adapter = ProductsAdapter(products)
                }

                }
            }
        }

        val bundle: Bundle?= intent.extras
        val user_search = bundle?.get("search").toString()

        val prodref=RootRef.child("Nilgiris").orderByChild(user_search).startAt(1.0)
        prodref.addValueEventListener(productListener)

        recycler_view.apply(){
            layoutManager=LinearLayoutManager(this@SearchActivity)
            adapter=ProductsAdapter(products)
        }
        Log.d("MainActivity", "XXX$products")

    }

}
