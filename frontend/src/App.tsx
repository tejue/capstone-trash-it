import './App.css'
import RoundResults from "./components/RoundResults.tsx";
import Game from "./pages/Game.tsx";
import {Route, Routes} from "react-router-dom";

export default function App() {

    return (
            <Routes>
                <Route path={"/rounds"} element={<RoundResults/>}></Route>
                <Route path={"/game"} element={<Game/>}></Route>
            </Routes>
    )
}
