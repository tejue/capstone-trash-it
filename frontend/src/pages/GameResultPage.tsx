import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import {Background} from "../components/Background.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import GameResult from "../components/GameResult.tsx";

export default function GameResultPage() {

    const navigate = useNavigate();

    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])

    useEffect(() => {
        getTrashCans();
    }, []);

    function getTrashCans() {
        axios.get("/api/trashcan")
            .then(response => {
                setTrashCans(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error.response.status);
            });
    }

    function handleNextPage() {
        navigate('/main-menu');
    }

    return (
        <>
            <Background/>
            <StyledDivResult>
                <StyledSection>
                    <GameResult/>
                </StyledSection>
                <ImageContainer>
                    {trashCans?.map((trashCan) => (
                        <StyledImage key={trashCan.id} src={`${trashCan.image}`} alt={`${trashCan.name}`}/>))}
                </ImageContainer>
            </StyledDivResult>
            <ButtonBuzzer handleClick={handleNextPage} buttonText={"main menu"}/>
        </>
    );
}

const StyledDivResult = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translate(-50%, -50%);
`

const StyledSection = styled.section`
  display: flex;
  justify-content: space-evenly;
  width: 100%;
`

const ImageContainer = styled.div`
  display: flex;
  height: 32vh;
  overflow: hidden;
`

const StyledImage = styled.img`
  height: 100%;
`
