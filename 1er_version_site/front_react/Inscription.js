import React from 'react';
import ReactDOM from 'react-dom';
import logo from './logo.png';
import axios from 'axios';
import Connexion from './Connexion';

class Inscription extends React.Component{
	constructor(props){
		super(props);
		this.state={
			pref:'',login:'',mdp1:'',mdp2:'',mail:'',vue:'',incomplet:''
		};
}

handleSubmit=(event)=>{
	event.preventDefault();

	const login=this.state.login;
	const mdp1=this.state.mdp1;
	const mdp2=this.state.mdp2;
	const mail=this.state.mail;
	const pref=this.state.pref;

	if(mdp1==='' || login==='' || mail===''){
		this.setState({incomplet:'visible'});
	}else{
		this.setState({incomplet:'hidden'});
		if(!(mdp1===mdp2)){
			alert("mots de passe ne correspandent pas");
			this.setState({mdp2:""});
		}else{
			var chaine="http://localhost:8080/melydo/CreateUser?log="+login+"&mdp="+mdp1+"&mail="+mail+"&pref="+pref+"";
		axios.post(chaine).then(response => {
											console.log(response.data);

											if(response.data.code===1){
												this.setState({vue:'visible'})
												var chaine="http://localhost:8080/melydo/Login?log="+login+"&mdp="+mdp1+"";
												axios.post(chaine).then(response => {this.props.envoi_donne(response.data);})
											}

											if(response.data["message"]==="mail existant "){
												this.setState({mail:''})
												alert('mail existant');
											}
											if(response.data["message"]==="login existant"){
												this.setState({login:''})
												alert('login existant');
											}

											})
		}
	}

}

handleChangePref=(event)=>{
	const value=event.currentTarget.value;
	this.setState({pref:value});
}

handleChangePrenom=(event)=>{
	const value=event.currentTarget.value;
	this.setState({prenom:value});
}
handleChangeLogin=(event)=>{
	const value=event.currentTarget.value;
	this.setState({login:value});
}
handleChangeMdp1=(event)=>{
	const value=event.currentTarget.value;
	this.setState({mdp1:value});
}
handleChangeMdp2=(event)=>{
	const value=event.currentTarget.value;
	this.setState({mdp2:value});
}
handleChangeMail=(event)=>{
	const value=event.currentTarget.value;
	this.setState({mail:value});
}

	render(){
		return(

			<div>
	    	<div className="text-center modal-dialog">
				<div className="col-sm-8 connexion">
					<div className="modal-content" onKeyPress={this._handleKeyPress}>
						<div className="col-12 logo-img-login">
							<img className="mb-4" src={logo} alt="logo"/>
						</div>
						<h1 className="h3 mb-3">Bienvenue sur Melydo !</h1>

						<form onSubmit={this.handleSubmit}>
	            <div className="form-group validate-input">
	              <input value={this.state.login} onChange={this.handleChangeLogin} className='form-control' type="text" name="login" id="login" placeholder="Login" ref="login" required/>
	            </div>

	            <div className="form-group">
	              <input  value={this.state.mail} onChange={this.handleChangeMail} type='text' placeholder="mail"/>
	            </div>

	            <div className="form-group validate-input">
	              <input value={this.state.mdp1} onChange={this.handleChangeMdp1} className='form-control' type="password" name="mdp" id="mdp" ref="mdp" placeholder="Mot de Passe" required minLength="8" pattern="([a-z]*[A-Z]*[0-9]*(\W)*)*" title="Au moins une majuscule, une minuscule, un chiffre et un caractere special"required />
	            </div>

	            <div className="form-group">
	              <input value={this.state.mdp2} onChange={this.handleChangeMdp2} className='form-control' type="password" name="mdpbis" id="mdpbis" ref="mdpbis" placeholder="Retapez le mot de passe" required />
	            </div>

							<div className="form-group">
						 	 <input value={this.state.pref} onChange={this.handleChangePref} className='text' type="text" name="pref" id="pref" ref="pref" placeholder="preference" required />
						  </div>


	            <div>
								<button className='valide'>Enregistrer</button>
	            </div>
							</form>



	          <div className="col-12 liens">
							<input className="lien" type="submit" onClick={()=>this.props.inscrip_fonct()} value="Déjà inscrit ?"/>
						</div>

						<div style={{visibility:this.state.vue}}>


						</div>

					</div>
				</div>
			</div>
	      </div>


			);
	}

}


export default Inscription;
