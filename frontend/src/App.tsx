import './App.css'
import GamesResult from "./components/GamesResult.tsx";
import Game from "./pages/Game.tsx";
import {Route, Routes} from "react-router-dom";

export default function App() {

    return (
            <Routes>
                <Route path={"/main-menu"} element={<GamesResult/>}></Route>
                <Route path={"/game"} element={<Game/>}></Route>
            </Routes>
    )
}
