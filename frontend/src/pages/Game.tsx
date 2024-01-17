import Trash from "../components/Trash.tsx";
import TrashCan from "../components/TrashCan.tsx";
import {TrashType} from "../types/TrashType.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import {DndContext, DragEndEvent} from "@dnd-kit/core";
import {useEffect, useState} from "react";
import axios from "axios";
import {PlayersResultType} from "../types/PlayersResultType.ts";

export default function Game() {

    const [trashes, setTrashes] = useState<TrashType[]>([]);
    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])
    const [playersResults, setPlayersResults] = useState<PlayersResultType>({});

    const [initialTrashes, setInitialTrashes] = useState<boolean>(false);
    const [gameEnd, setGameEnd] = useState<boolean>(false);

    useEffect(() => {
        if (!initialTrashes) {
            getTrashes();
            setInitialTrashes(true);
        }
        getTrashCans();
    }, [initialTrashes]);

    function getTrashes() {
        axios.get("api/trash")
            .then(response => setTrashes((response.data)))
    }

    function getTrashCans() {
        axios.get("api/trashcan")
            .then(response => setTrashCans((response.data)))
    }

    function handleDragEnd(event: DragEndEvent) {
        const {active, over} = event

        if (active && over) {
            setPlayersResults((prevResults) => {
                const newResults = {...prevResults};
                newResults[over.id] = {
                    trashCanId: over.id as string,
                    trashIds: [...(newResults[over.id]?.trashIds || []), active.id as string],
                };
                return newResults;
            });
            setTrashes((prevTrashes) => {
                const trashToRecycle = prevTrashes.filter((trash) => trash.id !== active.id)
                handleGameEnd(trashToRecycle);
                return trashToRecycle;
            });
        }
    }

    function handleGameEnd(trashToRecycle: TrashType []) {
        if (trashToRecycle.length === 0) setGameEnd(true)
    }

    console.log(playersResults)

    return (
        <DndContext
            onDragEnd={handleDragEnd}
        >
            {gameEnd ? (
                <p>Well Done! All trash is sorted.</p>
            ) : (
                <>
                    <Trash trashes={trashes}/>
                    <TrashCan trashCans={trashCans}/>
                </>)}
        </DndContext>
    )
}
