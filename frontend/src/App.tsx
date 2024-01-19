import './App.css'
import GamesResult from "./components/GamesResult.tsx";
import Game from "./pages/Game.tsx";
import {Route, Routes} from "react-router-dom";
import GameResult from "./pages/GameResult.tsx";

export default function App() {

    return (
            <Routes>
                <Route path={"/main-menu"} element={<GamesResult/>}></Route>
                <Route path={"/game"} element={<Game/>}></Route>
                <Route path={"/game-result"} element={<GameResult/>}></Route>
            </Routes>
    )
}
