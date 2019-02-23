import React, { Component } from 'react';
import './App.css';

class App extends Component {
   
   sendRequest(path){
      console.log(path);
   }

   state={
      folderList:[
         "Android",
         "AndroidStudioProjects",
         "Home",
         "Downloads",
         "Video"
      ]
   }

   render(){
      return(
         <div className="App">
            <Player click={this.sendRequest.bind(this,this.state.folderList[0])}>{this.state.folderList[0]}</Player>
            <Player click={this.sendRequest.bind(this,this.state.folderList[1])}>{this.state.folderList[1]}</Player>
            <Player click={this.sendRequest.bind(this,this.state.folderList[2])}>{this.state.folderList[2]}</Player>
            <Player click={this.sendRequest.bind(this,this.state.folderList[3])}>{this.state.folderList[3]}</Player>
            <Player click={this.sendRequest.bind(this,this.state.folderList[4])}>{this.state.folderList[4]}</Player>
         </div>
      )
   }
}

const Player = (props) =>{
      return(
         <div className="CardView" onClick={props.click}>
            <p className="FileName">{props.children}</p>
         </div>
      )
   
};

export default App;
