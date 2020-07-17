import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import logo from './logo.png';
import './login.css';

class Connexion extends React.Component{

	constructor(props){
		super(props);
		this.state={status:"",
					passageLogin:'',
					passageMdp:'',
					vue:'hidden'
				};

	}

handleSubmit=(event)=>{
	event.preventDefault();

	const log=this.state.passageLogin;
	const m=this.state.passageMdp;


	if(log==="" ||m===""){
		this.setState({vue:'visible'});

	}else{
		this.setState({vue:'hidden'});
		//	axios.post("http://localhost:8080/Birdy/Login",{params:{log:log,mdp:m}}).then(function(response){console.log(response);}).catch(function(error){console.log(error);})
		var chaine="http://localhost:8080/melydo/Login?log="+log+"&mdp="+m+"";
		axios.post(chaine).then(response => {
							console.log(response.data);
							if(response.data["message"]==="login inexistant"){
								alert("login inexistant")
							}else{
								if(response.data["message"]==="mot de passe ne correspand pas"){
								alert("mauvais mdp");
								}else{
									if(response.data["code"]===-1 ){
									alert("ereure,reessai,ou surement deja connecté");
									}else{
										this.props.envoi_donne(response.data);
									}

								}
							}
		})

	 }
}

handleChangeLog=(event)=>{
	const value=event.currentTarget.value;
	this.setState({passageLogin:value});
}

handleChangeMdp=(event)=>{
	const value=event.currentTarget.value;
	this.setState({passageMdp:value});
}


	render(){
		return(

		<div className="text-center modal-dialog" >
			<div className="col-sm-8 connexion" >
				<div className="modal-content" onKeyPress={this._handleKeyPress}>
					<div className="col-12 logo-img-login">
						<img className="mb-4" src={logo} alt="logo"/>
					</div>
					<h1 className="h3 mb-3">Bienvenue </h1>
					<form onSubmit={this.handleSubmit}>
						<div className="form-group">
							<input value={this.state.passageLogin} onChange={this.handleChangeLog} className='form-control' type="text" name="login" id="login" ref="login" placeholder="Login" required />
						</div>
						<div className="form-group">
							<input value={this.state.passageMdp} onChange={this.handleChangeMdp} className='form-control' type="password" name="mdp" id="mdp" ref="mdp" placeholder="Mot de passe" required />
						</div>
						<div>
							<button className='valide' >Connexion</button>
						</div>
						</form>
						<div className="col-12 liens">
							<input type="submit" className="lien" onClick={()=>this.props.conn_fonct()} value="Pas encore inscrit ?"/>
						</div>
					</div>
				</div>
			</div>



			);
	}

}


export default Connexion;
