import './App.css'
import MainMenuPage from "./pages/MainMenuPage.tsx";
import GamePage from "./pages/GamePage.tsx";
import {Route, Routes} from "react-router-dom";
import GameResultPage from "./pages/GameResultPage.tsx";
import HomePage from "./pages/HomePage.tsx";

export default function App() {
    return (
        <Routes>
            <Route path={"/"} element={<HomePage/>}/>
            <Route path={"/main-menu"} element={<MainMenuPage/>}/>
            <Route path={"/game"} element={<GamePage/>}/>
            <Route path={"/game-result/:gameId"} element={<GameResultPage/>}/>
        </Routes>
    )
}
