import React from 'react';
import ReactDOM from 'react-dom';
import Connexion from './Connexion';
import Inscription from './Inscription';
import PagePrincipale from './PagePrincipale';
import PageProfil from './PageProfil';
import App from './App';

class PageAffichage extends React.Component{
	constructor(props){
		super(props);
		this.state={affichage:"",key:"",nbr_connecte:0,nbr_de_conexion:0,nbr_inscrits:0,nbr_message_ecrit:0,
		nbr_message_commente:0,login:""};

		this.Change_pour_Inscription=this.Change_pour_Inscription.bind(this);
		this.Change_pour_connexion=this.Change_pour_connexion.bind(this);
		this.recu_donne=this.recu_donne.bind(this);
	}

	recu_donne(data){
		this.setState({key:data.session.message});

		this.setState({nbr_connecte:data.nbr_connecte});
		this.setState({nbr_de_conexion:data.nbr_connexion});
		this.setState({nbr_inscrits:data.nbr_inscrits});
		this.setState({nbr_message_ecrit:data.nbr_message});
		this.setState({nbr_message_commente:data.nbr_message_commente});
		this.setState({login:data.login});
		console.log(this.state);
	}
	Change_pour_Inscription(){
		this.setState({affichage:"inscription"})
	}

	Change_pour_connexion(){
		this.setState({affichage:"connexion"});
	}

	render(){
		const choix=this.state.affichage;
		var elue="";
		if(this.state.key!=""){
			elue=<PagePrincipale variables={this.state} page_connexion={this.Change_pour_connexion}/>;
		}else{
			if(choix==="inscription" || choix===""){
				elue=<Inscription inscrip_fonct={this.Change_pour_connexion} envoi_donne={this.recu_donne}/>;
			}
			if(choix==="connexion"){
				elue=<Connexion  conn_fonct={this.Change_pour_Inscription} envoi_donne={this.recu_donne}/>;
			}
		}
		return(
			<div>
				<div>
					{elue}
				</div>

			</div>
			);
	}

}


export default PageAffichage;
