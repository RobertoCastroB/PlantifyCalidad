package mx.itesm.bcr.plantifybcr.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.LoginApp
import mx.itesm.bcr.plantifybcr.Planta
import mx.itesm.bcr.plantifybcr.databinding.FragmentDashboardBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeFragmentDirections
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdaptador

class DashboardFragment : Fragment(),ListenerRecycler {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var adaptador: plantaWikiAdaptador
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = _binding?.rvPlantaWiki
        adaptador = plantaWikiAdaptador()

        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adaptador
        adaptador.listener = this
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            homeViewModel.tokken.observe(viewLifecycleOwner, Observer {
                _tokken = it.toString()
            })
            descargarDatosNuber()
        },150)
    }

    private fun descargarDatosNuber() {
        val baseDatos = Firebase.database
        val referenciaWiki = baseDatos.getReference("/Wiki/")
        referenciaWiki.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var arrPlantas = mutableListOf<Planta>()
                    for(planta in snapshot.children){
                        var d:Map<String,String> = planta.value as Map<String, String>
                        var plantaArr = planta.getValue(Planta::class.java)
                        if(plantaArr !=null){
                            arrPlantas.add(plantaArr)
                        }
                    }
                    //AQUI MANDAMOS EL ARRAY AL ADAPTADOR
                    //adaptador.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error $error")
            }

        })
    }

    override fun itemClickedPlanta(position: Int) {
        val plantaWiki = adaptador.titles[position]
        println("click en $plantaWiki")
        val accion = DashboardFragmentDirections.actionWikiFragToInfoWikiPlantaFrag(plantaWiki)
        findNavController().navigate(accion)
    }

    override fun itemClickedGrupo(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
