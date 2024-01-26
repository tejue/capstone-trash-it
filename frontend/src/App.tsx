import './App.css'
import MainMenu from "./pages/MainMenu.tsx";
import Game from "./pages/Game.tsx";
import {Route, Routes} from "react-router-dom";
import GameResult from "./pages/GameResult.tsx";
import HomePage from "./pages/HomePage.tsx";

export default function App() {
    return (
        <Routes>
            <Route path={"/"} element={<HomePage/>}/>
            <Route path={"/main-menu"} element={<MainMenu/>}/>
            <Route path={"/game"} element={<Game/>}/>
            <Route path={"/game-result/:gameId"} element={<GameResult/>}/>
        </Routes>
    )
}
