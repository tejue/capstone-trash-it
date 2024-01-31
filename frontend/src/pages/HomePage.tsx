import {useNavigate} from "react-router-dom";
import styled from "styled-components";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import {Background} from "../components/Background.ts";
import axios from "axios";
import {useEffect, useState} from "react";
import {GamePointsType} from "../types/GamePointsType.ts";

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
                        console.error("Data could not be deleted:", error)
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
                console.error("Request failed: ", error.response);
            })
    }

    return (
        <>
            <Background $backgroundColor={"none"}/>
            <StyledSection>
                <GameBox>Start cleaning up and...</GameBox>
            </StyledSection>
            <ButtonBuzzer handleClick={handleClick} buttonText={"trash it"}/>
        </>
    )
}

const StyledSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`

const GameBox = styled.p`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  line-height: 1.4;
  padding: 20px;
  background-color: #E6F0E9;
  width: 300px;
  height: 300px;
  clip-path: polygon(50% 0%, 90% 20%, 100% 50%, 100% 80%, 60% 100%, 20% 90%, 0% 60%, 10% 25%);
`
