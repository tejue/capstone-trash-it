import {useEffect, useState} from "react";
import axios from "axios";
import {TrashCanType} from "../types/TrashCanType.ts";

export default function Game() {

    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])

    useEffect(() => {
            getTrashes()
        }, []
    )
    ;

    function getTrashes() {
        axios.get("api/trashcan")
            .then(response => setTrashCans((response.data)))
    }

    console.log(trashCans);
    return (
        <>
            {trashCans.map((trashCan) => (
                <section key={trashCan.id}>
                    <p>{`${trashCan.name}`}</p>
                </section>))}
        </>
    )
}