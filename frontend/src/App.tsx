import './App.css'
import RoundResults from "./components/RoundResults.tsx";
import {Route, Routes} from "react-router-dom";

export default function App() {

    return (
        <>
            <Routes>
                <Route path={"/rounds"} element={<RoundResults/>}></Route>
            </Routes>
        </>
    )
}