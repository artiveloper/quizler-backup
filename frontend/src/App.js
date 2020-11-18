import React, {useEffect, useState} from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
} from "react-router-dom";
import Home from "./pages/Home";
import Admin from "./pages/Admin";


function App() {

    const [message, setMessage] = useState("")

    useEffect(() => {
        fetch("http://localhost:8080/api/test")
            .then(response => response.text())
            .then(message => {
                setMessage(message);
            })
    }, [])

    return (
        <Router>
            <div>
                <Switch>
                    <Route path="/admin">
                        <Admin />
                    </Route>
                    <Route path="/">
                        <Home />
                    </Route>
                </Switch>

            </div>
        </Router>
    );
}

export default App;
