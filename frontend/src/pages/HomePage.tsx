import {useNavigate} from "react-router-dom";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import {Background} from "../components/Background.ts";
import axios from "axios";
import {useEffect, useState} from "react";
import {GamePointsType} from "../types/GamePointsType.ts";
import lottieBird from "../assets/lottieBird.json";
import Lottie from "lottie-react";
import GameBox from "../components/GameBox.tsx";
import styled from "styled-components";

export default function HomePage() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const navigate = useNavigate();
    const [allGamesResult, setAllGamesResult] = useState<GamePointsType[]>([])

    useEffect(() => {
        getAllGamesResult()
    }, []);

    function handleClick() {
        if (allGamesResult.length >= 20) {
            const forceDeletion = window.confirm("No trash left. To play another round, you need to empty the trash cans by deleting all results. Do you want to proceed?");
            if (forceDeletion) {
                axios.put(`/api/game/${playerId}/gamesResult`)
                    .then(() => {
                        setAllGamesResult([])
                    })
                    .catch(error => {
                        console.error("Data could not be deleted:", error.response.status)
                    })
                    .finally(() => {
                        navigate("/game")
                    })
            }
        } else {
            navigate("/game")
        }
    }

    function getAllGamesResult() {
        axios.get(`/api/game/${playerId}/gamesResult`)
            .then(response => {
                setAllGamesResult(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error.response.status);
            })
    }

    return (
        <>
            <Background $backgroundColor={"none"}/>
            <StyledGameBoxSection>
                <GameBox
                    $text={"Under the midday sun, a gentle storm approaches! Winds whip trash into chaos. Take action! Gather and sort swiftly before the storm intensifies!"}/>
            </StyledGameBoxSection>
            <StyledLottieBirdSection>
                <Lottie animationData={lottieBird} loop={true}/>
            </StyledLottieBirdSection>
            <ButtonBuzzer handleClick={handleClick} buttonText={"trash it"}/>
        </>
    )
}

const StyledGameBoxSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`

const StyledLottieBirdSection = styled.section`
  position: absolute;
  width: 100%;
  top: 0;
  transform: translate(0, 90%);
`