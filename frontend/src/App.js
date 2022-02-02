import logo from './logo.svg';
import React from "react";
import './App.css';

class App extends React.Component {
  state = {
    groups: []
  };

  async componentDidMount() {
    const response = await fetch('/api/groups');
    const body = await response.json();
    this.setState({groups: body});
  }

  render() {
    const {groups} = this.state;
    return (
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <div className="App-intro">
              <h2>Группы</h2>
              {groups.map(group =>
                  <div key={group.id}>
                    {group.name}
                    {group.students.map(student =>
                      <div key={student.id}>
                        {`${student.last_name} ${student.first_name} ${student.patronymic}`}
                      </div>
                    )}
                  </div>
              )}
            </div>
          </header>
        </div>
    );
  }
}
export default App;