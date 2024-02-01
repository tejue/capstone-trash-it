import Trash from "../components/Trash.tsx";
import TrashCan from "../components/TrashCan.tsx";
import {TrashType} from "../types/TrashType.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import {GameType} from "../types/GameType.ts";
import {DndContext, DragEndEvent, PointerSensor, TouchSensor, useSensor, useSensors} from "@dnd-kit/core";
import {useEffect, useState} from "react";
import axios from "axios";
import {PlayerResultType} from "../types/PlayerResultType.ts";
import {useNavigate} from 'react-router-dom';
import styled from "styled-components";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import windsGrey from "../assets/windsGrey.svg";
import {Background} from "../components/Background.ts";
import GameBox from "../components/GameBox.tsx";
import Lottie from "lottie-react";
import lottieWindLoading from "../assets/lottieWindLoading.json";
import Snackbar from "../components/Snackbar.tsx";

export default function GamePage() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const navigate = useNavigate();

    const touchSensor = useSensor(TouchSensor, {
        activationConstraint: {
            delay: 250,
            tolerance: 5,
        },
    });

    const sensors = useSensors(touchSensor, useSensor(PointerSensor));

    const [trashes, setTrashes] = useState<TrashType[]>([]);
    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])
    const [playerResult, setPlayerResult] = useState<PlayerResultType>({});
    const [initialTrashes, setInitialTrashes] = useState<boolean>(false);
    const [gameEnd, setGameEnd] = useState<boolean>(false);
    const [games, setGames] = useState<GameType[]>([])
    const [loading, setLoading] = useState<boolean>(true);
    const [showSnackbar, setShowSnackbar] = useState<boolean>(false)

    useEffect(() => {
        if (!initialTrashes) {
            getTrashes();
            setInitialTrashes(true);
        }
        getTrashCans();
    }, [initialTrashes]);

    function getTrashes() {
        axios.get("/api/trash")
            .then(response => {
                setTrashes(response.data)
                axios.put(`/api/game/${playerId}/dataResult`, response.data)
                    .then(response => {
                        setGames(response.data.games)
                    })
            })
            .catch(error => {
                console.error("Request failed: ", error.response.status)
                setShowSnackbar(true);
            })
            .finally(() => {
                setLoading(false);
            })
    }

    function getTrashCans() {
        axios.get("/api/trashcan")
            .then(response => {
                setTrashCans(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error.response.status)
                setShowSnackbar(true);
            });
    }

    function postPlayerResult() {
        axios.put(`/api/game/${playerId}/` + games.length, playerResult)
            .catch(error => {
                console.error("Data could not be transmitted:", error.response.status)
                setShowSnackbar(true);
            })
            .finally(() => {
                navigate('/game-result/' + games.length);
            });
    }

    function handleDragEnd(event: DragEndEvent) {
        const {active, over} = event

        if (active && over) {
            setPlayerResult((prevResults) => {
                const newResults = {...prevResults};
                newResults[over.id] = {
                    trashCanId: over.id as string,
                    trashIds: [...(newResults[over.id]?.trashIds || []), active.id as string],
                };
                return newResults;
            });
            setTrashes((prevTrashes) => {
                const trashToRecycle = [...prevTrashes];

                const draggedTrashIndex = prevTrashes.findIndex(trash => trash.id === active.id);
                if (draggedTrashIndex !== -1) {
                    trashToRecycle.splice(draggedTrashIndex, 1);
                    trashToRecycle.splice(draggedTrashIndex, 0, {
                        id: `placeholder-${draggedTrashIndex}`,
                        name: "",
                        image: "",
                        trashType: ""
                    });
                }

                handleGameEnd(trashToRecycle);
                return trashToRecycle;
            })
        }
    }

    function handleGameEnd(trashToRecycle: TrashType[]) {
        setTimeout(() => {
            const noLeftTrashes = trashToRecycle.every(trash => trash.name === "");
            setGameEnd(noLeftTrashes);
        }, 250);
    }

    function handleCloseSnackbar() {
        setShowSnackbar(false);
    }

    return (
        <>
            <Background/>
            {showSnackbar && <Snackbar onClick={handleCloseSnackbar}/>}
            {loading && (<StyledLottieWindSection>
                <Lottie animationData={lottieWindLoading} loop={true}/>
            </StyledLottieWindSection>)}
            {!gameEnd && <StyledImage src={windsGrey} alt={"winds"}/>}
            <DndContext
                sensors={sensors}
                onDragEnd={handleDragEnd}
            >
                {gameEnd ? (
                    <>
                        <StyledGameBoxSection>
                            <GameBox $text={"Well Done! All trash is sorted."}/>
                        </StyledGameBoxSection>
                        <ButtonBuzzer handleClick={postPlayerResult} buttonText={"see your result"}/>
                    </>
                ) : (
                    <StyledDivGameArea>
                        <Trash trashes={trashes}/>
                        <TrashCan trashCans={trashCans}/>
                    </StyledDivGameArea>
                )}
            </DndContext>
        </>
    )
}

const StyledLottieWindSection = styled.section`
  position: absolute;
  width: 100%;
  top: 0;
  transform: translate(0, 60%);
`

const StyledDivGameArea = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  height: 100vh;
`

const StyledImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
`

const StyledGameBoxSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`