import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import {Background} from "../components/Background.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import GameResult from "../components/GameResult.tsx";
import Snackbar from "../components/Snackbar.tsx";

export default function GameResultPage() {

    const navigate = useNavigate();

    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])
    const [errorMessage, setErrorMessage] = useState<string>("");
    const [showSnackbar, setShowSnackbar] = useState<boolean>(false)

    useEffect(() => {
        getTrashCans();
    }, []);

    function getTrashCans() {
        axios.get("/api/trashcan")
            .then(response => {
                setTrashCans(response.data)
            })
            .catch(error => {
                    console.error("Request failed: ", error.response.status)
                    setErrorMessage("Ups, looks like something went wrong. Try again or come back later!")
                    setShowSnackbar(true);
                }
            )
    }

    function handleCloseSnackbar() {
        setShowSnackbar(false);
    }

    function handleNextPage() {
        navigate('/main-menu');
    }

    return (
        <>
            <Background/>
            {showSnackbar && <Snackbar onClickOk={handleCloseSnackbar} message={errorMessage}/>}
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
