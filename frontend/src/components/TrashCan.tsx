import styled from "styled-components";
import {TrashCanType} from "../types/TrashCanType.ts";
import DroppableTrashCan from "./DroppableTrashCan.tsx";

type TrashCanProps = {
    trashCans: TrashCanType [];
}

export default function TrashCan(props: Readonly<TrashCanProps>) {

    return (
        <StyledSection>
            {props.trashCans.map((trashCan) => (
                <DroppableTrashCan key={trashCan.id} trashCan={trashCan}/>
            ))
            }
        </StyledSection>
    )
}

const StyledSection = styled.section`
  position: fixed;
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  gap: 20px;
  bottom: 190px;
  height: 10vh;
`
