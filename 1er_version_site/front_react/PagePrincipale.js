import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
//import img from './image/fond.jpg';
import './PagePrincipale.css';


class PagePrincipale extends React.Component{
	constructor(props){
		super(props);
		this.state={jours:"100",commentaire:""};
		this.listComm=this.listComm.bind(this);
	}

	componentDidMount() {
		var chaine="http://localhost:8080/melydo/ListMessageRecent?jours="+this.state.jours+"";
		axios.get(chaine).then(response => {
												this.listComm(response.data);
											})


	}


	listComm(data){
		console.log(data.comm0);
		this.setState({commentaire:data});
	}

	handleSubmit=(event)=>{
		event.preventDefault();
			this.setState({vue:'hidden'});
			 var chaine="http://localhost:8080/melydo/LogOut?cle="+this.props.variables.key+"";

			 axios.post(chaine).then(response => {
				 if(response.data.code===-1){

			 					this.props.page_connexion();
				}

			 })

		 }


	render(){
		return(
			<div>
				<a>PagePrincipale</a>
				<div className="lesquatres">
					<div className="details">
					<form onSubmit={this.handleSubmit}>
					<div>
						<button className='valide' >Deconnexion</button>
					</div>
					</form>

						<dl>
							<dt>Login</dt>
							<dd>{this.props.variables.login}</dd>
							<dt>Nombre de fois connecté</dt>
							<dd>{this.props.variables.nbr_de_conexion}</dd>
							<dt>Nombre de personnes en ligne</dt>
							<dd>{this.props.variables.nbr_connecte}</dd>
							<dt>Nombre d'inscrits</dt>
							<dd>{this.props.variables.nbr_inscrits}</dd>
							<dt>Nombre de messages que vous avez ecrits</dt>
							<dd>{this.props.variables.nbr_message_ecrit}</dd>
							<dt>Nombre de messages commente</dt>
							<ol>{this.props.variables.nbr_message_commente}</ol>
						</dl>
					</div>
					<div className="zone_commentaire">
							<ul>



							</ul>
					</div>
				</div>

			</div>
			);
	}

}


export default PagePrincipale;





/*

didComponentMount(){
	this.setState(:);
}

componentWillReceiveProps(){
	this.setState(: );
}

*/
