import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {RoundType} from "../types/RoundType.ts";

export default function RoundResults() {

    const [rounds, setRounds] = useState<RoundType[]>([])

    const playerId: string = "2"

    useEffect(() => {
        getAllRounds()
    }, []);

    function getAllRounds() {
        axios.get(`/api/game/${playerId}/rounds`)
            .then(response => {
                setRounds(response.data);
            })
    }

    return (
        <>
            {rounds.length === 0 ? (
                <RoundBox>You have no saved result so far</RoundBox>
            ) : (
                rounds.map((round) => (
                    <StyledSection key={round.roundNumber}>
                        <RoundBox>{`Round: ${round.roundNumber}`}</RoundBox>
                        <RoundBox>{`You trashed ${round.trashedTotal} / ${round.garbageTotal}`}</RoundBox>
                    </StyledSection>
                )))}
        </>
    );
}

const StyledSection = styled.section`
  display: flex;
  justify-content: space-between;
`
const RoundBox = styled.p`
  border: solid 1px;
  border-radius: 5px;
  padding: 20px;
  text-align: center;
 `